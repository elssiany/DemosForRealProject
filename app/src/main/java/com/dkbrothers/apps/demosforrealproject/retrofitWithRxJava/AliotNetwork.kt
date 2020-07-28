package com.dkbrothers.apps.demosforrealproject.retrofitWithRxJava


import com.google.gson.JsonObject
import io.reactivex.Observable
import retrofit2.http.*


/**
 * Interface que contiene las peticiones hacia el
 * servidor el cual retorna
 * el datos puro o una clase POJO GSON
 */
interface AliotNetwork {

    companion object {
        const val TOKEN_AUTHOTIZATION = "Authorization"
    }


    @GET("getComments")
    fun consultarComentarios(): Observable<JsonObject>


}












