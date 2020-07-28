package com.dkbrothers.apps.demosforrealproject.viewPinchZoomable

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dkbrothers.apps.demosforrealproject.R
import kotlinx.android.synthetic.main.activity_change_scale_gesture.*


class ChangeScaleGestureActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_scale_gesture)
        initViews()
    }

    private fun initViews(){
        tv.setOnTouchListener(getMultiTouchListener())
    }


    private val onGestureControl = object :
        MultiTouchListener.OnGestureControl {
        override fun onLongClick() {

        }
        override fun onClick() {
            Toast.makeText(applicationContext, "Clic en una vista",
                Toast.LENGTH_SHORT).show()
        }
    }

    private val onMultiTouchListener = object :
        MultiTouchListener.OnMultiTouchListener {
        override fun actionDown(view: View) {

        }
    }

    /**
     * Create a new instance and scalable touchview
     *
     * @return scalable multitouch listener
     */
    private fun getMultiTouchListener(): MultiTouchListener {
        val multiTouchListener = MultiTouchListener(this,tv, true)
        multiTouchListener.setOnGestureControl(onGestureControl)
        multiTouchListener.setOnMultiTouchListener(onMultiTouchListener)
        return multiTouchListener
    }

}