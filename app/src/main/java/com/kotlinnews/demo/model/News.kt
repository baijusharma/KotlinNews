package com.kotlinnews.demo.model


import com.google.gson.annotations.SerializedName

data class News(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)