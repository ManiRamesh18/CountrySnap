package com.example.newsapp.data.repo

import android.content.Context
import android.net.ConnectivityManager
import com.example.newsapp.data.CollegesInterface
import com.example.newsapp.data.model.College
import com.example.newsapp.data.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class CollegeListRepository @Inject constructor(private val apiService: ApiService): CollegesInterface {

    override suspend fun fetchCollegesByCountry(countryName: String): List<College> {

        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getAllColleges()

                if(response.isSuccessful)
                {
                    response.body()?.countryList?: emptyList()
                }
                else{
                    emptyList()
                }
            }
            catch (e:Exception)
            {
                emptyList()
            }
        }
    }

    override suspend fun getCountryFlags(): List<College>
    {
        return withContext(Dispatchers.IO)
        {
            try {
                val response = apiService.getAllCountriesWithFlag()

                if(response.isSuccessful)
                {
                    response.body()?.countryList?: emptyList()
                }
                else
                {
                    emptyList()
                }
            }catch (e : Exception)
            {
                emptyList()
            }
        }
    }
}

