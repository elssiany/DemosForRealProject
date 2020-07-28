package com.dkbrothers.apps.demosforrealproject.viewPinchZoomable

import android.util.Log
import android.view.ScaleGestureDetector
import android.view.View
import kotlin.math.max
import kotlin.math.min

class ScaleListener(private val mView: View) :
    ScaleGestureDetector.SimpleOnScaleGestureListener() {
    private var mScaleFactor = 1.0f
    override fun onScale(scaleGestureDetector: ScaleGestureDetector): Boolean {
        mScaleFactor *= scaleGestureDetector.scaleFactor
        mScaleFactor = max(
            0.8f,
            min(mScaleFactor, 5.0f)
        )
        mView.scaleX = mScaleFactor
        mView.scaleY = mScaleFactor
        Log.d("ScaleListener", "Scale------------$mScaleFactor")
        return true
    }

}