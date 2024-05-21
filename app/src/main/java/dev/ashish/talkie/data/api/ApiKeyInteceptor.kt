package dev.ashish.talkie.data.api

import dev.ashish.talkie.di.NetworkApiKey
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInteceptor constructor(@NetworkApiKey private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder().header("X-Api-Key", apiKey)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}