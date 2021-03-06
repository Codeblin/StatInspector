package com.android.codeblins.profilerapp.apiclient

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Created by Codeblin S. on 3/10/2019.
 */
class AuthenticationInterceptor(private val authToken: String) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val builder = original.newBuilder()
            .header("Authorization", authToken)

        val request = builder.build()
        return chain.proceed(request)
    }
}