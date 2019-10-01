package com.kotlinnews.demo.common

import com.kotlinnews.demo.interfaces.NewService
import com.kotlinnews.demo.remote.RetrofitClient
import java.lang.StringBuilder


// Use object keyword to create a Singleton class
object Common {

    val BASE_URL = "https://newsapi.org/"
    val API_KEY = "59e3e457fc384fd4b54e1a08bf0eac44"

    val newService: NewService
        get() = RetrofitClient.getRetrofitInstance(BASE_URL).create(NewService::class.java)

    //https://newsapi.org/v2/top-headlines?sources=bbc-news&apiKey=59e3e457fc384fd4b54e1a08bf0eac44
    fun getNewsAPI(source: String): String {
        val apiUrl = StringBuilder("")
            .append("v2/top-headlines?sources=" + source)
            .append("&apiKey=")
            .append(API_KEY)
            .toString()

        return apiUrl

    }
}