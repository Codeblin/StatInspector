package com.android.codeblins.profilerapp.apiclient

import com.android.codeblins.profilerapp.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.text.TextUtils
import okhttp3.Credentials
import okhttp3.OkHttpClient




/**
 * Created by Codeblin S. on 3/10/2019.
 */
object ApiManager {
    private val httpClient = OkHttpClient.Builder()

    private var builder = Retrofit.Builder()
        .baseUrl(BuildConfig.SERVER_URL)
        .addConverterFactory(GsonConverterFactory.create())

    var retrofit = builder.build()

    fun <S> createService(
        serviceClass: Class<S>, username: String, password: String
    ): S {
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            val authToken = Credentials.basic(username, password)
            return createService(serviceClass, authToken)
        }

        return createService(serviceClass, null)
    }

    fun <S> createService(
        serviceClass: Class<S>, authToken: String?
    ): S {
        if (!TextUtils.isEmpty(authToken)) {
            val interceptor = AuthenticationInterceptor(authToken ?: "")

            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor)

                builder.client(httpClient.build())
                retrofit = builder.build()
            }
        }

        return retrofit.create(serviceClass)
    }
}