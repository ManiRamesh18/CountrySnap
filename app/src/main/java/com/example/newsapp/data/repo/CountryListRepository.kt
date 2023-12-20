package com.example.newsapp.data.repo

import com.example.newsapp.data.CollegesInterface
import com.example.newsapp.data.model.Country
import com.example.newsapp.data.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class CountryListRepository @Inject constructor(private val apiService: ApiService): CollegesInterface {

    override suspend fun getCountryFlags(): List<Country>
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

