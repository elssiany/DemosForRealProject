package com.dkbrothers.apps.demosforrealproject.viewPinchZoomable

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Path
import android.graphics.Rect
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.TextView

internal class MultiTouchListener( /*@Nullable View deleteView, RelativeLayout parentView,*/
    context: Context?, objectView: TextView, private val mIsTextPinchZoomable: Boolean
) : OnTouchListener {
    private val mGestureListener: GestureDetector
    private val minimumScale = 0.5f
    private val maximumScale = 10.0f
    private var mActivePointerId = INVALID_POINTER_ID
    private var mPrevX = 0f
    private var mPrevY = 0f
    private var mPrevRawX = 0f
    private var mPrevRawY = 0f
    private val mScaleGestureDetector: CustomScaleGestureDetector
    private val location = IntArray(2)
    private val outRect: Rect
    private val objectView: TextView
    private var mOnGestureControl: OnGestureControl? = null
    private var mOnMultiTouchListener:OnMultiTouchListener? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(view: View, event: MotionEvent): Boolean {
        mScaleGestureDetector.onTouchEvent(view, event)
        mGestureListener.onTouchEvent(event)
        val action = event.action
        val x = event.rawX.toInt()
        val y = event.rawY.toInt()
        when (action and event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                mOnMultiTouchListener?.actionDown(view)
                mPrevX = event.x
                mPrevY = event.y
                mPrevRawX = event.rawX
                mPrevRawY = event.rawY
                mActivePointerId = event.getPointerId(0)
                view.bringToFront()
            }
            MotionEvent.ACTION_MOVE -> {
                val pointerIndexMove = event.findPointerIndex(mActivePointerId)
                if (pointerIndexMove != -1) {
                    val currX = event.getX(pointerIndexMove)
                    val currY = event.getY(pointerIndexMove)
                    if (!mScaleGestureDetector.isInProgress) {
                        adjustTranslation(
                            view,
                            currX - mPrevX,
                            currY - mPrevY
                        )
                    }
                }
            }
            MotionEvent.ACTION_CANCEL -> mActivePointerId = INVALID_POINTER_ID
            MotionEvent.ACTION_UP -> mActivePointerId = INVALID_POINTER_ID
            MotionEvent.ACTION_POINTER_UP -> {
                val pointerIndexPointerUp =
                    action and MotionEvent.ACTION_POINTER_INDEX_MASK shr MotionEvent.ACTION_POINTER_INDEX_SHIFT
                val pointerId = event.getPointerId(pointerIndexPointerUp)
                if (pointerId == mActivePointerId) {
                    val newPointerIndex = if (pointerIndexPointerUp == 0) 1 else 0
                    mPrevX = event.getX(newPointerIndex)
                    mPrevY = event.getY(newPointerIndex)
                    mActivePointerId = event.getPointerId(newPointerIndex)
                }
            }

        }
        return true
    }

    private fun isViewInBounds(view: View, x: Int, y: Int): Boolean {
        view.getDrawingRect(outRect)
        view.getLocationOnScreen(location)
        outRect.offset(location[0], location[1])
        return outRect.contains(x, y)
    }


    private inner class ScaleGestureListener :
        CustomScaleGestureDetector.SimpleOnScaleGestureListener() {
        private var mPivotX = 0f
        private var mPivotY = 0f
        private val mPrevSpanVector = Vector2D()
        override fun onScaleBegin(
            view: View?,
            detector: CustomScaleGestureDetector?
        ): Boolean {
            mPivotX = detector!!.getFocusX()
            mPivotY = detector.getFocusY()
            mPrevSpanVector.set(detector.getCurrentSpanVector())
            return mIsTextPinchZoomable
        }

        override fun onScale(
            view: View?,
            detector: CustomScaleGestureDetector?
        ): Boolean {
            val info = TransformInfo()
            info.deltaScale = detector!!.getScaleFactor()
            info.deltaAngle = Vector2D.getAngle(
                mPrevSpanVector,
                detector.getCurrentSpanVector()
            )
            info.deltaX = detector.getFocusX() - mPivotX
            info.deltaY = detector.getFocusY() - mPivotY
            info.pivotX = mPivotX
            info.pivotY = mPivotY
            info.minimumScale = minimumScale
            info.maximumScale = maximumScale
            move(view, info)
            return !mIsTextPinchZoomable
        }
    }

    private class TransformInfo {
        var deltaX = 0f
        var deltaY = 0f
        var deltaScale = 0f
        var deltaAngle = 0f
        var pivotX = 0f
        var pivotY = 0f
        var minimumScale = 0f
        var maximumScale = 0f
    }

    internal interface OnMultiTouchListener {
        fun actionDown(view: View)
    }


    internal interface OnGestureControl {
        fun onClick()
        fun onLongClick()
    }

    fun setOnGestureControl(onGestureControl: OnGestureControl?) {
        mOnGestureControl = onGestureControl
    }
    fun setOnMultiTouchListener(onMultiTouchListener: OnMultiTouchListener?) {
        mOnMultiTouchListener = onMultiTouchListener
    }
    private inner class GestureListener : SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            if (mOnGestureControl != null) {
                mOnGestureControl!!.onClick()
            }
            return true
        }

        override fun onLongPress(e: MotionEvent) {
            super.onLongPress(e)
            if (mOnGestureControl != null) {
                mOnGestureControl!!.onLongClick()
            }
        }
    }

    companion object {
        private const val INVALID_POINTER_ID = -1
        private fun adjustAngle(degreesD: Float): Float {
            var degrees = degreesD
            if (degrees > 180.0f) {
                degrees -= 360.0f
            } else if (degrees < -180.0f) {
                degrees += 360.0f
            }
            return degrees
        }

        private fun move(view: View?, info: TransformInfo) {
            computeRenderOffset(view, info.pivotX, info.pivotY)
            adjustTranslation(view, info.deltaX, info.deltaY)
            var scale = view!!.scaleX * info.deltaScale
            scale =
                Math.max(info.minimumScale, Math.min(info.maximumScale, scale))
            view.scaleX = scale
            view.scaleY = scale
            val rotation =
                adjustAngle(view.rotation + info.deltaAngle)
            view.rotation = rotation
        }

        private fun adjustTranslation(
            view: View?,
            deltaX: Float,
            deltaY: Float
        ) {
            val deltaVector = floatArrayOf(deltaX, deltaY)
            view!!.matrix.mapVectors(deltaVector)
            view.translationX = view.translationX + deltaVector[0]
            view.translationY = view.translationY + deltaVector[1]
        }

        private fun computeRenderOffset(
            view: View?,
            pivotX: Float,
            pivotY: Float
        ) {
            if (view!!.pivotX == pivotX && view.pivotY == pivotY) {
                return
            }
            val prevPoint = floatArrayOf(0.0f, 0.0f)
            view.matrix.mapPoints(prevPoint)
            view.pivotX = pivotX
            view.pivotY = pivotY
            val currPoint = floatArrayOf(0.0f, 0.0f)
            view.matrix.mapPoints(currPoint)
            val offsetX = currPoint[0] - prevPoint[0]
            val offsetY = currPoint[1] - prevPoint[1]
            view.translationX = view.translationX - offsetX
            view.translationY = view.translationY - offsetY
        }
    }

    init {
        mScaleGestureDetector = CustomScaleGestureDetector(ScaleGestureListener())
        mGestureListener = GestureDetector(context, GestureListener())
        //this.deleteView = deleteView;
        this.objectView = objectView
        outRect = Rect(0, 0, 0, 0)
    }
}