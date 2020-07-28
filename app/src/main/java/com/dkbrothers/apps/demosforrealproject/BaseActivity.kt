package com.dkbrothers.apps.demosforrealproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import com.dkbrothers.apps.demosforrealproject.retrofitWithLivedataCourutinas.ApiClientRetrofitBuilder
import com.dkbrothers.apps.demosforrealproject.retrofitWithRxJava.AliotClient
import com.dkbrothers.apps.demosforrealproject.utils.limpiarStrings
import com.google.gson.JsonObject
import hayError
import obtenerError


abstract class BaseActivity : AppCompatActivity() {

    var contentLoadingProgressBar: ContentLoadingProgressBar? = null
    lateinit var apiClientRxJava: AliotClient
    lateinit var apiClientLiveData: ApiClientRetrofitBuilder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiClientRxJava = AliotClient(this)
        apiClientLiveData = ApiClientRetrofitBuilder(this)
    }

    fun verificarError(jsonObject: JsonObject, callback: () -> Unit = {}): Boolean {
        if (hayError(jsonObject)) {
            val error = obtenerError(jsonObject).limpiarStrings()
            //showInfoDialog(null, error).onClickAceptar = callback
            Toast.makeText(this,error, Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }


    /**
     * Metodo usado para inicializar el <contentLoadingProgressBar> en un Activity
     * */
    fun initContentLoadingProgressBar() {
        contentLoadingProgressBar = findViewById(R.id.contentLoadingProgressBar)
    }

    fun showContentLoadingProgressBar() {
        contentLoadingProgressBar?.visibility = View.VISIBLE
        contentLoadingProgressBar?.show()
    }

    fun hideContentLoadingProgressBar() {
        contentLoadingProgressBar?.visibility = View.GONE
        contentLoadingProgressBar?.hide()
    }

}
