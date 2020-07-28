package com.dkbrothers.apps.demosforrealproject.intentToMyApp

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dkbrothers.apps.demosforrealproject.R
import kotlinx.android.synthetic.main.activity_get_u_r_l_video.*

class GetURLVideoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_u_r_l_video)
        val data= intent?.data
        // Figure out what to do based on the intent type
        if(intent?.type?.startsWith("video/") == true) {
            // Handle intents with video data ...
            inputLinkVideo.setText(data.toString())
        }
    }

}