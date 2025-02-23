package ru.disav.kinopoiskviewer.data.provider

import okhttp3.Interceptor
import okhttp3.Response

object KinopoiskInterceptor : Interceptor {
    private const val HEADER = "X-API-KEY"
    private const val KEY = "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b"

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request
            .newBuilder()
            .addHeader(HEADER, KEY)
            .build()

        return chain.proceed(request)
    }
}