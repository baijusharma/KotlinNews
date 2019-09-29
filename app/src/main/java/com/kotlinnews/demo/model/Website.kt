package com.kotlinnews.demo.model


import com.google.gson.annotations.SerializedName

data class Website(
    val sources: List<Source>,
    val status: String
)