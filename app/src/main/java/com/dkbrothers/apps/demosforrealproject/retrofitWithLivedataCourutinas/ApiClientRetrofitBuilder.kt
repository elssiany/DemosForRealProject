package com.dkbrothers.apps.demosforrealproject.retrofitWithLivedataCourutinas

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dkbrothers.apps.demosforrealproject.BuildConfig
import com.dkbrothers.apps.demosforrealproject.R
import com.dkbrothers.apps.demosforrealproject.retrofitWithRxJava.ApiInterceptor
import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

class ApiClientRetrofitBuilder(val context: Context){

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(ApiInterceptor(context))
        .addInterceptor(
            LoggingInterceptor.Builder()
                .loggable(BuildConfig.DEBUG)
                .setLevel(Level.BASIC)
                .log(Platform.INFO)
                .request("Api Request")
                .response("Api Response")
                .build()
        ).connectTimeout(2, TimeUnit.MINUTES)
        .readTimeout(2, TimeUnit.MINUTES).build()

    fun config() = liveData(Dispatchers.IO) {
        try {
            emit(apiInterface.consultarComentarios())
        } catch (exception: Exception) {
            emit(getError(exception))
        }
    }

    //:LiveData<Observable<JsonObject>>

    private fun rxConfig(jsonObjectObservable: Observable<JsonObject>) = liveData(Dispatchers.IO) {
           emit(jsonObjectObservable.subscribeOn(Schedulers.newThread())
               .observeOn(AndroidSchedulers.mainThread())
               .onErrorReturn(ApiClientRetrofitBuilder(context)::getError))
        }


     fun getError(throwable: Throwable): JsonObject {
        val error = "${throwable.message} Class: ${throwable.javaClass.name}"
        Log.e("Api Error", error, throwable)
        val jsonObject = JsonObject()
        jsonObject.addProperty("error", "1")
        val errorMenssage: String = when (throwable) {
            is UnknownHostException ->{
                context.getString(R.string.error_no_internet)//error
            }
            is SocketTimeoutException ->{
                context.getString(R.string.error_servidor)
            }
            is JsonSyntaxException ->{
                context.getString(R.string.error_dato_malformado_servicio)
            }
            else ->{
                context.getString(R.string.error_inesperado_servicios)
            }
        }
        //Toast.makeText(context,error,Toast.LENGTH_LONG).show()
        jsonObject.addProperty("response", errorMenssage)
        return jsonObject
    }

    private val apiInterface: ApiNetworkService = Retrofit.Builder()
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BuildConfig.BASE_URL)
        .build()
        .create(ApiNetworkService::class.java)

     fun consultarComentarios() = rxConfig(apiInterface.consultarComentarios())

}