package com.dkbrothers.apps.demosforrealproject.changeThemeDynamically

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import com.dkbrothers.apps.demosforrealproject.R

class CustomLayoutInflater (
    private val delegate: AppCompatDelegate
) : LayoutInflater.Factory2 {

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        return when (name) {
            "TextView" -> CustumTextView(context, attrs)
            "LinearLayout" -> CustomLinearLayout(context, attrs)
            "RelativeLayout" -> CustomRelativeLayout(context, attrs)
            "Button" -> CustumButton(context, attrs, R.attr.buttonStyle)
            else -> delegate.createView(parent, name, context, attrs)
        }
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return onCreateView(null, name, context, attrs)
    }
}