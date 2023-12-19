package com.example.newsapp.network

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class NetworkInterceptor: AppCompatActivity(), Interceptor
{
    override fun intercept(chain: Interceptor.Chain): Response {
       if(!isNetworkAvailable())
       {
           throw IOException("No Network Available")
       }

        val request = chain.request()
        return chain.proceed(request)
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        return connectivityManager?.activeNetworkInfo?.isConnected == true
    }
}

