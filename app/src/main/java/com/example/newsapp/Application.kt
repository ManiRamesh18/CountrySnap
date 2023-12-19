package com.example.newsapp

import android.app.Application
import com.example.newsapp.network.NetworkInterceptor
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NewsApplication: Application()
{

}