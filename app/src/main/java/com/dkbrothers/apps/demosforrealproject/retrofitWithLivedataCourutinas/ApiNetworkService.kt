package com.dkbrothers.apps.demosforrealproject.retrofitWithLivedataCourutinas

import com.google.gson.JsonObject
import io.reactivex.Observable
import retrofit2.http.GET

interface ApiNetworkService {

    @GET("getComments")
    fun consultarComentarios(): Observable<JsonObject>

}