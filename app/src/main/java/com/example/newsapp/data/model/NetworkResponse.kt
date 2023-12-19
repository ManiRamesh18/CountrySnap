package com.example.newsapp.data.model

import com.google.gson.annotations.SerializedName

class NetworkResponse
{
    @SerializedName("error")
    val error = false

    @SerializedName("msg")
    var msg : String ?= null

    @SerializedName("data")
    var countryList : List<College> ?= null


}