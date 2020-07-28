package com.dkbrothers.apps.demosforrealproject.screenshot

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.dkbrothers.apps.demosforrealproject.R
import com.dkbrothers.apps.demosforrealproject.utils.convertBase64ToBitmap
import getStringPreference


class SeeScreenshotActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_screenshot)
        initViews()
    }

    private fun initViews(){

        findViewById<ImageView>(R.id.img_screenshot).setImageBitmap(
            convertBase64ToBitmap(getStringPreference("screenshot","")))

    }

}
