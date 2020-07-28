package com.dkbrothers.apps.demosforrealproject.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager


abstract class BaseDialogFragment : DialogFragment() {


    override fun show(manager: FragmentManager, tag: String?) {
        val fragment = manager.findFragmentByTag(tag)
        val ft = manager.beginTransaction()
        if (fragment != null) {
            ft.remove(fragment)
        }
        ft.commitAllowingStateLoss()
        /*
        fragment?.let {
            try {
                ft.remove(fragment).commit()
            } catch (e: IllegalStateException) {
                ft.remove(fragment).commitAllowingStateLoss()
            }
        }
         */
        super.show(manager, tag)
    }

    override fun onStart() {
        super.onStart()
        isCancelable = false
        this.dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}