package com.example.newsapp.data

import com.example.newsapp.data.model.Country

interface CollegesInterface
{
    suspend fun getCountryFlags(): List<Country>
}