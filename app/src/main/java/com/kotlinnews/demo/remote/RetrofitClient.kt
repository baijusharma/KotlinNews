package com.kotlinnews.demo.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


//Singleton class
object RetrofitClient {

    private var retrofit: Retrofit? = null // nullable

    fun getRetrofitInstance(baseUrl: String?): Retrofit {
        if (retrofit == null) {

            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        }

        return retrofit!! //Assertion use when u know the value will not be null

    }

}