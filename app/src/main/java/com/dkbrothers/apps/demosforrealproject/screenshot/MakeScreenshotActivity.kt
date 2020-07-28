package com.dkbrothers.apps.demosforrealproject.screenshot

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.view.PixelCopy
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dkbrothers.apps.demosforrealproject.BaseActivity
import com.dkbrothers.apps.demosforrealproject.R
import com.dkbrothers.apps.demosforrealproject.utils.convertBitmapToBase64
import deletePrefence
import savePreference


class MakeScreenshotActivity : BaseActivity() {

    private lateinit var containerViews:FrameLayout
    private var bitmapScreenshot:Bitmap? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_screenshot)
        initViews()
    }

    private fun initViews() {
        initContentLoadingProgressBar()
        containerViews = findViewById(R.id.container_views)
        savePreference("screenshot","")
    }

    fun onMakeScreenshot(view: View) {
        Toast.makeText(applicationContext,getString(R.string.capturando_pantalla),
            Toast.LENGTH_SHORT).show()
        bitmapScreenshot = makeScreenshot(containerViews)
    }

    fun onSeeScreenshot(view: View) {
        if (bitmapScreenshot!=null) {
            savePreference("screenshot", convertBitmapToBase64(bitmapScreenshot!!))
            startActivity(Intent(this, SeeScreenshotActivity::class.java))
        }else{
            Toast.makeText(applicationContext,getString(R.string.debe_tomar_una_captura_primero),
                Toast.LENGTH_SHORT).show()
        }
    }

    /*
    private fun makeScreenshot(view: View){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            val locationOfViewInWindow = IntArray(2)
            view.getLocationInWindow(locationOfViewInWindow)
            PixelCopy.request(window, Rect(locationOfViewInWindow[0],
                locationOfViewInWindow[1], locationOfViewInWindow[0] + view.width,
                locationOfViewInWindow[1] + view.height), bitmap, { copyResult ->
                if (copyResult == PixelCopy.SUCCESS) {
                    bitmapScreenshot = bitmap
                }
                hideContentLoadingProgressBar()
                Toast.makeText(applicationContext,getString(R.string.captura_de_pantalla_hecha),
                    Toast.LENGTH_SHORT).show()
                // possible to handle other result codes ...
            }, Handler())
        }else{
            view.isDrawingCacheEnabled = true
            view.buildDrawingCache()
            bitmapScreenshot = view.drawingCache
            hideContentLoadingProgressBar()
            Toast.makeText(applicationContext,R.string.captura_de_pantalla_hecha,
                Toast.LENGTH_SHORT).show()
        }
    }
    */



    private fun makeScreenshot(view: View): Bitmap? {
        /*
        val dm: DisplayMetrics = resources.displayMetrics
        view.measure(
            View.MeasureSpec.makeMeasureSpec(dm.widthPixels, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(dm.heightPixels, View.MeasureSpec.EXACTLY))

             //Para tomar el tamaño de toda la pantalla
             //v.getMeasuredWidth() v.getMeasuredHeight()
        */
        view.layout(0, 0, view.width, view.height)
        val bitmapScreenshot = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val c = Canvas(bitmapScreenshot)
        view.draw(c)
        return bitmapScreenshot
    }


}
