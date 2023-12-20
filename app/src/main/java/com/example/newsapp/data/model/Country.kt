package com.example.newsapp.data.model

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("country") val country: String,
    @SerializedName("name") val name: String,
    @SerializedName("flag") val flag : String
)