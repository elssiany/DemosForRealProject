package com.dkbrothers.apps.demosforrealproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.dkbrothers.apps.demosforrealproject.viewPinchZoomable.ChangeScaleGestureActivity
import com.dkbrothers.apps.demosforrealproject.changeThemeDynamically.ChangeThemeDynamicallyActivity
import com.dkbrothers.apps.demosforrealproject.retrofitWithLivedataCourutinas.ListLiveDataActivity
import com.dkbrothers.apps.demosforrealproject.retrofitWithRxJava.ListDataActivity
import com.dkbrothers.apps.demosforrealproject.screenshot.MakeScreenshotActivity
import com.dkbrothers.apps.demosforrealproject.viewPinchZoomable.DualTouchActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onNumeroSimcard(view: View) {
        startActivity(Intent(this,PhoneNumberSimCardActivity::class.java))
    }

    fun onMakeScreenshot(view: View) {
        startActivity(Intent(this,
            MakeScreenshotActivity::class.java))
    }

    fun onChangeThemeDynamically(view: View) {
        startActivity(Intent(this,ChangeThemeDynamicallyActivity::class.java))
    }

    fun onScaleGesture(view: View) {
        startActivity(Intent(this,ChangeScaleGestureActivity::class.java))
    }

    fun onDualTouch(view: View) {
        startActivity(Intent(this,DualTouchActivity::class.java))
    }

    fun onRetrofitRxJava(view: View) {
        startActivity(Intent(this,ListDataActivity::class.java))
    }

    fun onRetrofitLiveData(view: View) {
        startActivity(Intent(this,ListLiveDataActivity::class.java))
    }

}
