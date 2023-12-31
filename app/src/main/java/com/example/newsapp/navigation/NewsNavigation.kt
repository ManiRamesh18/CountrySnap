package com.example.newsapp.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.example.newsapp.data.model.Country
import com.example.newsapp.ui.NewsViewModel
import com.example.newsapp.ui.theme.NewsAppTheme
import kotlinx.coroutines.launch

@Composable
fun NewsNavigation()
{
    NewsAppTheme {

        val navController = rememberNavController()

        val viewModal = viewModel(modelClass = NewsViewModel::class.java)

        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

        ModalNavigationDrawer(drawerContent = {}, drawerState = drawerState)
        {
            NewsAppHost(navController, viewModel = viewModal)
        }
    }
}


@Composable
fun NewsAppHost(navController: NavHostController, viewModel: NewsViewModel)
{
    NavHost(navController = navController, startDestination = NewsNavigationActions.HOME)
    {
        composable(NewsNavigationActions.HOME)
        {
            HomeScreen(viewModel = viewModel)
        }
    }
}

@Composable
fun HomeScreen(viewModel: NewsViewModel)
{
    viewModel.collegeList.value.let { it->
        if(it.isLoading)
        {
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center)
            {
                CircularProgressIndicator()
            }
        }
        else if(it.postData.isNotEmpty())
        {
            Column (modifier = Modifier
                .fillMaxSize()
                .padding(10.dp))
            {
                TopBar()

                Column (modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.onPrimary)){

                    Spacer(modifier = Modifier.height(10.dp))

                    NewsList(data =it.postData, viewModel)
                }
            }
        }

    }

}

@Composable
fun NewsList(data : List<Country>, viewModel: NewsViewModel)
{
    val lazyState = rememberLazyListState()

    val scope = rememberCoroutineScope()

    val showButton by remember {
        derivedStateOf {
            lazyState.firstVisibleItemIndex>0
        }
    }

    var showBottomSheet by remember {
        mutableStateOf(false)
    }

    if(showBottomSheet)
    {
        ShowModalBottomSheet (viewModel){
            showBottomSheet = false
        }
    }

    Box(modifier = Modifier.fillMaxSize())
    {
        LazyColumn(state = lazyState){

            items(data){it->
                News(newsData = it,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(size = 20.dp))
                        .wrapContentHeight()
                        .padding(horizontal = 10.dp, vertical = 15.dp)){
                    viewModel.getFlagUnicodeBasedOnCountry(it.name)
                    showBottomSheet=true
                }
            }
        }

        AnimatedVisibility(visible = showButton,enter = fadeIn(), exit = fadeOut())
        {
            ScrollToTopButton {
               scope.launch {
                   lazyState.animateScrollToItem(0)
               }
            }
        }
    }
}

@Composable
fun ScrollToTopButton(onClick: () -> Unit) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(bottom = 50.dp), Alignment.BottomCenter
    ) {
        Button(onClick = { onClick() }, modifier = Modifier
                .shadow(10.dp, shape = CircleShape)
                .clip(shape = CircleShape)
                .size(65.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.Green,
            )
        ) {
            Icon(Icons.Filled.KeyboardArrowUp, "arrow up")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowModalBottomSheet(viewModel: NewsViewModel, onDismiss: ()-> Unit)
{
    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(onDismissRequest = onDismiss,
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle()},
        modifier = Modifier
            .height(500.dp)
            .padding(10.dp)
            .fillMaxWidth())
    {
        viewModel.flagUnicode.let {flag->
            if(flag.value.isEmpty())
            {
                Text(text = "Flag Not Found")
            }
            else
            {
                val painterResponse = rememberAsyncImagePainter(model =ImageRequest.Builder(
                    LocalContext.current)
                    .decoderFactory(SvgDecoder.Factory())
                    .data(flag.value)
                    .size(Size.ORIGINAL)
                    .build()
                )

                Image(painter = painterResponse,
                    contentDescription ="Flag Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds)
            }
        }
    }
}

@Composable
fun News(newsData : Country, modifier: Modifier = Modifier, onCardClick : ()-> Unit)
{
    Card (colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary),
        modifier = modifier
            .width(240.dp)
            .height(100.dp)
            .clickable(true, onClick = onCardClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)){

        Row (modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center)
        {
            Text(
                modifier = Modifier
                    .padding(vertical = 15.dp, horizontal = 20.dp),
                text = newsData.name,
                fontFamily = FontFamily.Default,
                textAlign = TextAlign.Center,
                fontSize = 20.sp)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar()
{
    Row (modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.primary))
    {
        CenterAlignedTopAppBar(
            title = {
                Text(text = "Country Snap",
                    fontFamily = FontFamily.Default,
                    fontSize = 20.sp,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight(500)
                ) })
    }
}