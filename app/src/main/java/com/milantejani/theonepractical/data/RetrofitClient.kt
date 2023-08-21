package com.milantejani.theonepractical.data

import com.milantejani.theonepractical.AppConstant
import com.milantejani.theonepractical.data.api.NetworkService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    fun getRetrofitClient(): NetworkService {
        return Retrofit.Builder()
            .baseUrl(AppConstant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NetworkService::class.java)
    }
}