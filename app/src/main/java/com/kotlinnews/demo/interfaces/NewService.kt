package com.kotlinnews.demo.interfaces

import com.kotlinnews.demo.model.News
import com.kotlinnews.demo.model.Website
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface NewService {

    @get:GET("v2/sources?apiKey=59e3e457fc384fd4b54e1a08bf0eac44")
    val sources: Call<Website>

    @GET
    fun getNewsFromSource(@Url url: String): Call<News>
}