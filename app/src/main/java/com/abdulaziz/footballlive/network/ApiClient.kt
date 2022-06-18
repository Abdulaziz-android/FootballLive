package com.abdulaziz.footballlive.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


object ApiClient {

    private const val BASE_URL = "https://apiv2.allsportsapi.com/"
    const val API_KEY = "4823b60930603ca9e0385bb16dbbc5ecc02789050caf53e5b0a326904235318c"

    private fun getRetrofit():Retrofit{

        val client: OkHttpClient.Builder = OkHttpClient.Builder()
        client.connectTimeout(15, TimeUnit.SECONDS)
        client.readTimeout(15, TimeUnit.SECONDS)
        client.writeTimeout(15, TimeUnit.SECONDS)

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()
    }

    val apiService = getRetrofit().create(ApiService::class.java)

}