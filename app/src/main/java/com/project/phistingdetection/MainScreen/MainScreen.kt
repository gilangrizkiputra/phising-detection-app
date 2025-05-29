package com.project.phistingdetection.MainScreen

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.SentimentSatisfiedAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.project.phistingdetection.component.FormInputItem
import com.project.phistingdetection.component.cardNewsItem
import com.project.phistingdetection.component.cardTipsItem
import com.project.phistingdetection.data.model.NewsItem
import com.project.phistingdetection.data.model.getDummyNews
import com.project.phistingdetection.data.model.getDummyTips
import com.project.phistingdetection.ui.theme.background
import com.project.phistingdetection.ui.theme.blue
import com.project.phistingdetection.ui.theme.darkGray
import com.project.phistingdetection.ui.theme.gray
import com.project.phistingdetection.ui.theme.poppinsFontFamily
import com.project.phistingdetection.ui.theme.turquoiseGreen
import com.project.phistingdetection.ui.theme.white
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    viewModelUrl: PhishingViewModel = viewModel(),
    viewModelNews : NewsViewModel = viewModel(),
    initialUrl: String = "",
) {
    var inputLinkUrl by remember {
        mutableStateOf(initialUrl)
    }

    MainScreenContent(
        inputLinkUrl = inputLinkUrl,
        onInputChange = { inputLinkUrl = it },
        onCheckUrl = { viewModelUrl.checkUrl(inputLinkUrl) },
        urlStatus = viewModelUrl.urlStatus,
        urlSafePercentage = viewModelUrl.urlSafePercentage,
        isLoading = viewModelUrl.isLoading,
        isUrlChecked = viewModelUrl.isUrlChecked,
        isSafe = viewModelUrl.isSafe,
        isNotSafe = viewModelUrl.isNotSafe,
        onReset = {
            inputLinkUrl = ""
            viewModelUrl.reset()
        },
        onCheckNews = { viewModelNews.fetchNews() },
        newsList = viewModelNews.newsList,
        isLoadingNews = viewModelNews.isLoading,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreenContent(
    inputLinkUrl: String,
    onInputChange: (String) -> Unit,
    onCheckUrl: () -> Unit,
    urlStatus: String,
    urlSafePercentage: String,
    isLoading: Boolean,
    isUrlChecked: Boolean,
    isSafe: Boolean,
    isNotSafe: Boolean,
    onReset: () -> Unit,
    onCheckNews: () -> Unit,
    newsList: List<NewsItem>,
    isLoadingNews: Boolean,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val refreshing = remember { mutableStateOf(false) }
    var selectedTabIndex by remember { mutableStateOf(0) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding()
            .background(background)
            .padding(bottom = 16.dp)
    ){
        SwipeRefresh(
            state = rememberSwipeRefreshState(refreshing.value),
            onRefresh = {
                refreshing.value = true
                CoroutineScope(Dispatchers.Main).launch {
                    delay(500)
                    onCheckNews()
                    onReset()
                    refreshing.value = false
                }
            }
        ) {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
            ) {
                item {
                    HeaderSection(
                        inputLinkUrl = inputLinkUrl,
                        onInputChange = onInputChange,
                        onCheckUrl = onCheckUrl,
                        urlStatus = urlStatus,
                        urlSafePercentage = urlSafePercentage,
                        isLoading = isLoading,
                        isUrlChecked = isUrlChecked,
                        isSafe = isSafe,
                        isNotSafe = isNotSafe,
                        onBrowse = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(inputLinkUrl))
                            context.startActivity(intent)
                        }
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                }

                stickyHeader {
                    TabSelection(
                        selectedTabIndex = selectedTabIndex,
                        onTabSelected = { selectedTabIndex = it }
                    )
                }

                when (selectedTabIndex) {
                    0 -> {
                        if (isLoadingNews) {
                            item {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .fillParentMaxWidth()
                                        .padding(top = 100.dp)
                                ) {
                                    CircularProgressIndicator(
                                        color = turquoiseGreen,
                                        strokeWidth = 3.dp,
                                        modifier = Modifier.size(30.dp)
                                    )
                                }
                            }
                        } else if (newsList.isEmpty()) {
                            item {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .fillMaxWidth()
                                        .padding(100.dp),
                                ) {
                                    Text(
                                        text = "Berita tidak ditemukan",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        fontFamily = poppinsFontFamily,
                                        color = darkGray
                                    )
                                }

                            }

                        }
                        else {
                            items(newsList) { news ->
                                news.image_url?.let {
                                    cardNewsItem(
                                        image = it,
                                        title = news.title,
                                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 10.dp)
                                    )
                                }
                            }
                        }
                    }

                    1 -> items(getDummyTips()) { tips ->
                        cardTipsItem(
                            iconImage = painterResource(tips.iconRes),
                            title = tips.title,
                            description = tips.description,
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 10.dp)

                        )
                    }
                }
            }
        }
    }
}


@Composable
fun HeaderSection(
    inputLinkUrl: String,
    onInputChange: (String) -> Unit,
    onCheckUrl: () -> Unit,
    urlStatus: String,
    urlSafePercentage: String,
    isLoading: Boolean,
    isUrlChecked: Boolean,
    isSafe: Boolean,
    isNotSafe: Boolean,
    onBrowse: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomEnd = 30.dp, bottomStart = 30.dp)),
        color = blue,
        shadowElevation = 4.dp,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .animateContentSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            ) {
                Text(
                    text = "Periksa Tautan Website",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = poppinsFontFamily,
                    color = turquoiseGreen,
                )
                Icon(
                    imageVector = Icons.Default.SentimentSatisfiedAlt,
                    contentDescription = "Sentimen",
                    tint = turquoiseGreen,
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .size(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                FormInputItem(
                    value = inputLinkUrl,
                    onValueChange = onInputChange,
                    placeable = "Masukkan Tautan Website",
                    keyboardType = KeyboardType.Text,
                    modifier = Modifier
                        .height(50.dp)
                        .weight(1f)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White)
                        .border(
                            width = 1.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(20.dp)
                        )
                )
                Spacer(modifier = Modifier.width(10.dp))
                Button(
                    onClick = onCheckUrl,
                    modifier = Modifier.height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = turquoiseGreen,
                        contentColor = white,
                    ),
                    enabled = !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = white,
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(20.dp)
                        )
                    } else {
                        Icon(Icons.Default.Send, contentDescription = "Send", tint = white)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            AnimatedVisibility(visible = isUrlChecked) {
                Column {
                    Text(
                        text = "URL: $inputLinkUrl",
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = poppinsFontFamily,
                        fontSize = 14.sp,
                        color = white,
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = urlStatus,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = poppinsFontFamily,
                        fontSize = 16.sp,
                        color = when {
                            isSafe -> Color.Green
                            isNotSafe -> Color.Red
                            else -> Color.White
                        },
                    )
                    Text(
                        text = urlSafePercentage,
                        fontWeight = FontWeight.Medium,
                        fontFamily = poppinsFontFamily,
                        fontSize = 14.sp,
                        color = white,
                    )

                    if (isSafe) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(
                            onClick = onBrowse,
                            modifier = Modifier.height(50.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = turquoiseGreen,
                                contentColor = white,
                            )
                        ) {
                            Text(
                                text = "Lanjutkan Browsing",
                                fontFamily = poppinsFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.sp,
                            )
                        }
                    }
                }
            }

            if (!isUrlChecked) {
                Text(
                    text = "Tautan/link belum terdeteksi",
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = poppinsFontFamily,
                    fontSize = 16.sp,
                    color = white,
                )
            }
        }
    }
}

@Composable
fun TabSelection(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
) {

    val tabTitles = listOf("Berita", "Tips & Trick")

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Divider(
            color = gray,
            thickness = 1.dp,
            modifier = Modifier
        )
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.fillMaxWidth(),
            containerColor = background,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    height = 2.dp,
                    color = turquoiseGreen
                )
            },
            divider = {}
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { onTabSelected(index) },
                    text = {
                        Text(
                            text = title,
                            fontFamily = poppinsFontFamily,
                            fontSize =  12.sp,
                            color = if (selectedTabIndex == index) turquoiseGreen else darkGray,
                            fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.SemiBold
                        )
                    }
                )
            }
        }
        Divider(
            color = gray,
            thickness = 1.dp,
            modifier = Modifier
        )
    }
}


//@Preview(showSystemUi = true)
//@Composable
//private fun MainScreenContentPreview() {
//    MainScreen()
//}