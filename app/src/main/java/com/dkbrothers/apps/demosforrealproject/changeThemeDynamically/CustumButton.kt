package com.dkbrothers.apps.demosforrealproject.changeThemeDynamically

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.dkbrothers.apps.demosforrealproject.utils.ThemeManager

class CustumButton
@JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatButton(context, attrs, defStyleAttr),
    ThemeManager.ThemeChangedListener {

    override fun onFinishInflate() {
        super.onFinishInflate()
        ThemeManager.addListener(this)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        ThemeManager.addListener(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        ThemeManager.removeListener(this)
    }

    override fun onThemeChanged(theme: ThemeManager.Theme) {
        setTextColor(
            ContextCompat.getColor(
                context,
                theme.buttonTheme.textColor
            )
        )
        backgroundTintList =
            ColorStateList.valueOf(
                ContextCompat.getColor(
                    context,
                    theme.buttonTheme.backgroundTint
                )
            )
    }
}