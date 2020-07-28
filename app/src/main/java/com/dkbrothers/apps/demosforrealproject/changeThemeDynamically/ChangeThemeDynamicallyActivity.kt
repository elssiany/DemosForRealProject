package com.dkbrothers.apps.demosforrealproject.changeThemeDynamically

import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.animation.doOnEnd
import androidx.core.view.LayoutInflaterCompat
import androidx.core.view.isVisible
import com.dkbrothers.apps.demosforrealproject.R
import com.dkbrothers.apps.demosforrealproject.utils.ThemeManager
import kotlinx.android.synthetic.main.activity_change_theme_dynamically.*
import kotlin.math.hypot


class ChangeThemeDynamicallyActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        LayoutInflaterCompat.setFactory2(
            LayoutInflater.from(this),
            CustomLayoutInflater(delegate))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_theme_dynamically)
        btn.setOnClickListener {
            val newTheme = when (ThemeManager.theme) {
                ThemeManager.Theme.DARK -> ThemeManager.Theme.LIGHT
                ThemeManager.Theme.LIGHT -> ThemeManager.Theme.DARK
            }
            setTheme(container,bg,newTheme, true)
        }
    }


    private fun setTheme(container:ViewGroup,imageView:ImageView,
                         theme: ThemeManager.Theme, animate: Boolean = true) {
        if (!animate) {
            ThemeManager.theme = theme
            return
        }

        if (imageView.isVisible) {
            return
        }

        val w = container.measuredWidth
        val h = container.measuredHeight

        val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        container.draw(canvas)

        imageView.setImageBitmap(bitmap)
        imageView.isVisible = true

        val finalRadius = hypot(w.toFloat(), h.toFloat())

        ThemeManager.theme = theme

        val anim = ViewAnimationUtils.createCircularReveal(imageView,
            w / 2, h / 2, finalRadius, 0f)

        anim.duration = 400L
        anim.doOnEnd {
            imageView.setImageDrawable(null)
            imageView.isVisible = false
        }
        anim.start()
    }


    override fun onPostResume() {
        super.onPostResume()
        setTheme(container,bg,ThemeManager.Theme.LIGHT, animate = false)
    }

}