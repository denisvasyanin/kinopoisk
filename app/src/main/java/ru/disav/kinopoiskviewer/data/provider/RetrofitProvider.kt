package ru.disav.kinopoiskviewer.data.provider

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.disav.kinopoiskviewer.data.network.KinopoiskApi

object RetrofitProvider {
    private const val BASE_URL = "https://kinopoiskapiunofficial.tech"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(KinopoiskInterceptor)
        .build()

    fun get(): KinopoiskApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(KinopoiskApi::class.java)
}

