package com.example.newsapp.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.newsapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDrawer(
    modifier: Modifier = Modifier,
    navigateToHome : ()-> Unit,
    navigateToArticle : () -> Unit,
    onClose : ()->Unit
)
{
    ModalDrawerSheet(modifier = modifier.background(Color.Red)) {
        NewsAppLogo()

        Spacer(modifier = Modifier.height(20.dp))
        NavigationDrawerItem(label = {
                                     Text(text = "Home")
                                     }, selected = true,
            onClick =  navigateToHome)
        Spacer(modifier = Modifier.height(20.dp))
        NavigationDrawerItem(label = { Text(text = "Article")}, selected = true, onClick =  navigateToArticle)
    }
}

@Composable
fun NewsAppLogo(modifier: Modifier = Modifier)
{
        Row (modifier = modifier){
            Icon(
                painter = painterResource(R.drawable.news_logo),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
}