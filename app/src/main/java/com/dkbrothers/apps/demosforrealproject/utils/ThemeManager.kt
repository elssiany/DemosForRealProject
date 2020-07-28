package com.dkbrothers.apps.demosforrealproject.utils

import androidx.annotation.ColorRes
import com.dkbrothers.apps.demosforrealproject.R

object ThemeManager {
    private val listeners = mutableSetOf<ThemeChangedListener>()
    var theme = Theme.LIGHT
        set(value) {
            field = value
            listeners.forEach { listener -> listener.onThemeChanged(value) }
        }

    interface ThemeChangedListener {

        fun onThemeChanged(theme: Theme)
    }

    data class ButtonTheme(
        @ColorRes
        val backgroundTint: Int,
        @ColorRes
        val textColor: Int
    )

    data class TextViewTheme(
        @ColorRes
        val textColor: Int
    )

    data class ViewGroupTheme(
        @ColorRes
        val backgroundColor: Int
    )

    enum class Theme(
        val buttonTheme: ButtonTheme,
        val textViewTheme: TextViewTheme,
        val viewGroupTheme: ViewGroupTheme
    ) {
        DARK(
            buttonTheme = ButtonTheme(
                backgroundTint = R.color.colorPrimaryDark,
                textColor = R.color.blanco
            ),
            textViewTheme = TextViewTheme(
                textColor = R.color.blanco
            ),
            viewGroupTheme = ViewGroupTheme(
                backgroundColor = android.R.color.background_dark
            )
        ),
        LIGHT(
            buttonTheme = ButtonTheme(
                backgroundTint = R.color.colorPrimary,
                textColor = R.color.negro
            ),
            textViewTheme = TextViewTheme(
                textColor = R.color.negro
            ),
            viewGroupTheme = ViewGroupTheme(
                backgroundColor = android.R.color.background_light
            )
        )
    }

    fun addListener(listener: ThemeChangedListener) {
        listeners.add(listener)
    }

    fun removeListener(listener: ThemeChangedListener) {
        listeners.remove(listener)
    }
}