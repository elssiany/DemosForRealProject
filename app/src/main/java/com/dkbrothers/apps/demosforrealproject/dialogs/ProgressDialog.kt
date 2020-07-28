package com.dkbrothers.apps.demosforrealproject.dialogs


import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.view.KeyEvent.KEYCODE_BACK
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.dkbrothers.apps.demosforrealproject.R
import com.dkbrothers.apps.demosforrealproject.databinding.DialogInfoBinding


class ProgressDialog(private  val t:String) : BaseDialogFragment() {

    private lateinit var binding: DialogInfoBinding

    var onDismiss: () -> Unit = {}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
         binding = DataBindingUtil.inflate(inflater,
            R.layout.dialog_info, container, false)
        //Set transparent background and no title
        if(dialog != null && dialog!!.window != null) {
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        }
        dialog!!.setOnKeyListener(object : DialogInterface.OnKeyListener {
            override fun onKey(dialog: DialogInterface?, keyCode: Int, event: KeyEvent?): Boolean {
                if (event!!.action == KeyEvent.ACTION_UP) {
                    if(keyCode == KEYCODE_BACK) {
                        onDismiss()
                        dismiss()
                        return true
                    }
                }
                return false
            }
        })
        if(t.isNotEmpty()){
            binding.progressBar.visibility = View.GONE
            binding.tvMsm.text = t
        }
        return binding.root
    }

}


fun AppCompatActivity?.showProgressDialog(): ProgressDialog {
    val dialog = ProgressDialog("")
    this?.let {
        dialog.show(it.supportFragmentManager, ProgressDialog::class.java.name)
    }
    return dialog
}

fun FragmentActivity?.CustomShowDialog(t:String): ProgressDialog {
    val dialog = ProgressDialog(t)
    this?.let {
        dialog.show(it.supportFragmentManager, ProgressDialog::class.java.name)
    }
    return dialog
}



fun FragmentActivity?.closeProgressDialog() {
    val progressDialog = this?.supportFragmentManager?.findFragmentByTag(ProgressDialog::class.java.name)
    if(progressDialog is ProgressDialog) {
        progressDialog.dismissAllowingStateLoss()
    }
}