package com.example.newsapp.data.model

import com.google.gson.annotations.SerializedName

data class College(
    val alpha_two_code: String,
    @SerializedName("country") val country: String,
    @SerializedName("domains") val domains: List<String>,
    @SerializedName("name") val name: String,
    @SerializedName("unicodeFlag") val unicodeFlag : String,
    @SerializedName("state-province") val stateProvince: String,
    @SerializedName("web_pages") val web_pages: List<String>,
    @SerializedName("flag") val flag : String
)