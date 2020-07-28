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

internal class DualTouchListener(
    context: Context
) : OnTouchListener {

    private val mGestureListener: GestureDetector
    private val outRect: Rect
    private var mOnGestureControl: OnGestureControl? = null
    private var mOnDualTouchListener: OnDualTouchListener? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(view: View, event: MotionEvent): Boolean {
        mGestureListener.onTouchEvent(event)
        val action = event.action
        val x = event.rawX.toInt()
        val y = event.rawY.toInt()
        mOnDualTouchListener?.actionTouch(event)
        return true
    }


    internal interface OnDualTouchListener {
        fun actionTouch(event: MotionEvent)
    }


    internal interface OnGestureControl {
        fun onClick()
        fun onLongClick()
    }

    fun setOnGestureControl(onGestureControl: OnGestureControl) {
        mOnGestureControl = onGestureControl
    }

    fun setOnDualTouchListener(onDualTouchListener: OnDualTouchListener) {
        this.mOnDualTouchListener = onDualTouchListener
    }

    private inner class GestureListener : SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            if (mOnGestureControl != null) {
                mOnGestureControl?.onClick()
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
    }

    init {
        mGestureListener = GestureDetector(context, GestureListener())
        outRect = Rect(0, 0, 0, 0)
    }
}