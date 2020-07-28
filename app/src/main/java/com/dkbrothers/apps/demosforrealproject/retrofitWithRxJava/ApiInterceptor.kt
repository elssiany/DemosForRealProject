package com.dkbrothers.apps.demosforrealproject.retrofitWithRxJava

import android.content.Context
import android.os.Build
import com.dkbrothers.apps.demosforrealproject.BuildConfig
import com.google.gson.JsonParser
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.RequestBody.Companion.toRequestBody
import okio.Buffer
import org.json.JSONObject

class ApiInterceptor(val context: Context) : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        val response = when (chain.request().method) {
            "POST", "PUT" -> {
                val buffer = Buffer()
                chain.request().body?.writeTo(buffer)
                val bufferUtf8 = buffer.readUtf8()
                val mediaType = "application/json; charset=UTF-8".toMediaTypeOrNull()
                val jsonObject = JsonParser().parse(bufferUtf8)
                val requestBody =jsonObject.toString().toRequestBody(mediaType)
                val headers = chain.request().newBuilder()
                    .headers(chain.request().headers)
                    .header("Content-Type", requestBody.contentType().toString())
                    .header("Content-Length", requestBody.contentLength().toString())
                    .header("X-MC-SO", "android")
                    .header("Cache-Control", "no-cache")
                    .header("X-MC-SO-V", Build.VERSION.RELEASE)
                    .header("X-MC-SO-API", Build.VERSION.SDK_INT.toString())
                    .header("X-MC-SO-PHONE-F", Build.MANUFACTURER)
                    .header("X-MC-SO-PHONE-M", Build.MODEL)
                        .header("X-WUX-KEY","7575AEC6A9956EE566004AEE2EC33CFBD37A9FD7C234A761F1B71AFBD14E597F")
                val request = headers
                    .method(chain.request().method, requestBody).build()
                chain.proceed(request)
            }
            else -> {
                val headers = chain.request().newBuilder()
                    .headers(chain.request().headers)
                    .header("X-MC-SO", "android")
                    .header("X-MC-SO-V", Build.VERSION.RELEASE)
                    .header("Cache-Control", "no-cache")
                    .header("X-MC-SO-API", Build.VERSION.SDK_INT.toString())
                    .header("X-MC-SO-PHONE-F", Build.MANUFACTURER)
                    .header("X-MC-SO-PHONE-M", Build.MODEL)
                    .header("X-MC-APP-V", BuildConfig.VERSION_NAME)
                        .header("X-WUX-KEY","7575AEC6A9956EE566004AEE2EC33CFBD37A9FD7C234A761F1B71AFBD14E597F")
                chain.proceed(headers.build())
        }
        }
        val jsonResponse = response.body?.string() ?: ""
        return response.newBuilder().body(jsonResponse.toResponseBody(response.body?.contentType())).build()
    }


}
