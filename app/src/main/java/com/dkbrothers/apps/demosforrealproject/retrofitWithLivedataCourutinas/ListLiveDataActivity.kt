package com.dkbrothers.apps.demosforrealproject.retrofitWithLivedataCourutinas

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.dkbrothers.apps.demosforrealproject.BaseActivity
import com.dkbrothers.apps.demosforrealproject.R
import com.dkbrothers.apps.demosforrealproject.dialogs.closeProgressDialog
import com.dkbrothers.apps.demosforrealproject.dialogs.CustomShowDialog
import com.dkbrothers.apps.demosforrealproject.dialogs.showProgressDialog
import io.reactivex.disposables.CompositeDisposable

class ListLiveDataActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_data)
    }

    fun hacerPeticion(view:View){
        Toast.makeText(this.applicationContext,getString(R.string.cargando),
            Toast.LENGTH_LONG).show()
        Handler().postDelayed({
            apiClientLiveData.consultarComentarios().observe(this, Observer {
                it.subscribe { response ->
                    Toast.makeText(this.applicationContext,"Ya respondio el servicio",
                        Toast.LENGTH_SHORT).show()
                    CustomShowDialog("Hola el LiveData sirvio, muestra el dialogo cuando la app esta activa")
                }
            })
        }, 10000)
    }



    override fun onDestroy() {
        super.onDestroy()
    }


}