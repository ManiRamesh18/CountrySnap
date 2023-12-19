package com.example.newsapp.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController

object NewsNavigationActions{
    const val HOME ="home"
    const val DETAILS = "details"
}

class NavigationActions(navController: NavController)
{

    val navigateToHome : ()-> Unit ={
        navController.navigate(NewsNavigationActions.HOME)

    }

    val navigateToDetails : ()-> Unit ={
        navController.navigate(NewsNavigationActions.DETAILS)
    }
}