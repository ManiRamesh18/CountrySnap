package com.example.newsapp.data

import com.example.newsapp.data.model.College

interface CollegesInterface
{
    suspend fun fetchCollegesByCountry(countryName : String): List<College>

    suspend fun getCountryFlags(): List<College>
}