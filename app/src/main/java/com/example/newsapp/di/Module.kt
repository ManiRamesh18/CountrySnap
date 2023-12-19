package com.example.newsapp.di

import com.example.newsapp.data.network.ApiService
import com.example.newsapp.data.repo.CollegeListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    fun provideCollegeListRepository(apiService: ApiService): CollegeListRepository
    {
        return CollegeListRepository(apiService)
    }

    @Provides
    fun provideRetrofitInstance(): Retrofit
    {
        val okHttpClient : OkHttpClient.Builder = OkHttpClient().newBuilder()
            .callTimeout(2, TimeUnit.MINUTES)
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS);

        return Retrofit.Builder()
            .baseUrl("https://countriesnow.space/api/v0.1/countries/")
            .client(okHttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService
    {
        return retrofit.create(ApiService::class.java)
    }

}