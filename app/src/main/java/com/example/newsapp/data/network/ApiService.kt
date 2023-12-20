package com.example.newsapp.data.network

import com.example.newsapp.data.model.NetworkResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService
{
    @GET("flag/images")
    suspend fun getAllCountriesWithFlag() : Response<NetworkResponse>
}