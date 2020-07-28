package com.dkbrothers.apps.demosforrealproject.retrofitWithRxJava

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import com.dkbrothers.apps.demosforrealproject.BaseActivity
import com.dkbrothers.apps.demosforrealproject.R
import com.dkbrothers.apps.demosforrealproject.dialogs.CustomShowDialog
import io.reactivex.disposables.CompositeDisposable

class ListDataActivity : BaseActivity() {

    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_data)
    }


    fun hacerPeticion(view:View){
        Toast.makeText(this.applicationContext,getString(R.string.cargando),
            Toast.LENGTH_LONG).show()
        Handler().postDelayed({
            disposable.add(apiClientRxJava.
            consultarComentarios()
                .subscribe{ response ->
                    Toast.makeText(this.applicationContext,"Ya respondio el servicio",
                        Toast.LENGTH_SHORT).show()
                    CustomShowDialog("Hola el LiveData sirvio, muestra el dialogo cuando la app esta activa")
                })
        }, 10000)
    }


    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }


}