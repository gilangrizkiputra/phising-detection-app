package com.project.phistingdetection.MainScreen

import android.content.Intent
import android.health.connect.datatypes.units.Percentage
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.SentimentSatisfiedAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.project.phistingdetection.component.FormInputItem
import com.project.phistingdetection.ui.theme.blue
import com.project.phistingdetection.ui.theme.poppinsFontFamily
import com.project.phistingdetection.ui.theme.turquoiseGreen
import com.project.phistingdetection.ui.theme.white
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    viewModel: PhishingViewModel = viewModel(),
    initialUrl: String = "",
) {
    var inputLinkUrl by remember {
        mutableStateOf(initialUrl)
    }

    MainScreenContent(
        inputLinkUrl = inputLinkUrl,
        onInputChange = { inputLinkUrl = it },
        onCheckUrl = { viewModel.checkUrl(inputLinkUrl) },
        urlStatus = viewModel.urlStatus,
        urlSafePercentage = viewModel.urlSafePercentage,
        isLoading = viewModel.isLoading,
        isUrlChecked = viewModel.isUrlChecked,
        isSafe = viewModel.isSafe,
        isNotSafe = viewModel.isNotSafe,
        onReset = {
            inputLinkUrl = ""
            viewModel.reset()
        },
    )
}

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
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val refreshState = rememberSwipeRefreshState(isRefreshing = isLoading)
    val refreshing = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(refreshing.value),
            onRefresh = {
                refreshing.value = true
                CoroutineScope(Dispatchers.Main).launch {
                    delay(500)
                    onReset()
                    refreshing.value = false
                }
            }
        ) {
            LazyColumn() {
                item {
                    Surface(
                        modifier = modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(bottomEnd = 30.dp, bottomStart = 30.dp)),
                        color = blue,
                        shadowElevation = 4.dp,
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Row (
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 20.dp)
                            ){
                                Text(
                                    text = "Periksa Link Website Kamu",
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
                                        .width(20.dp)
                                        .height(20.dp)
                                )
                            }

                            Spacer(modifier = Modifier.padding(top = 20.dp))
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                FormInputItem(
                                    value = inputLinkUrl,
                                    onValueChange = onInputChange,
                                    placeable = "Masukkan Link Website",
                                    keyboardType = KeyboardType.Text,
                                    modifier = Modifier
                                        .height(50.dp)
                                        .weight(1f)
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(Color.White)
                                        .border(
                                            width = 1.5.dp,
                                            color = Color.Gray,
                                            shape = RoundedCornerShape(20.dp)
                                        )
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Button(
                                    onClick = onCheckUrl,
                                    modifier = Modifier
                                        .height(50.dp)
                                        .padding(0.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = turquoiseGreen,
                                        contentColor = white,
                                    ),
                                    enabled = !isLoading
                                ) {
                                    if (isLoading){
                                        CircularProgressIndicator(
                                            color = white,
                                            strokeWidth = 2.dp,
                                            modifier = Modifier
                                                .width(20.dp)
                                                .height(20.dp)
                                        )
                                    }else{
                                        Icon(
                                            imageVector = Icons.Default.Send,
                                            contentDescription = "Send",
                                            tint = white,
                                        )
                                    }

                                }
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            if (isUrlChecked) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "URL: $inputLinkUrl",
                                        fontWeight = FontWeight.SemiBold,
                                        fontFamily = poppinsFontFamily,
                                        fontSize = 14.sp,
                                        color = white,
                                    )
                                }
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
                            } else {
                                Text(
                                    text = "Tautan/link belum terdeteksi",
                                    fontWeight = FontWeight.SemiBold,
                                    fontFamily = poppinsFontFamily,
                                    fontSize = 16.sp,
                                    color = white,
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            if (isUrlChecked && isSafe) {
                                Button(
                                    onClick = {
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(inputLinkUrl))
                                        context.startActivity(intent)
                                    },
                                    modifier = Modifier
                                        .height(50.dp)
                                        .padding(0.dp),
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
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Berita & Tips"
                        )
                    }
                }
            }
        }
    }
}

//@Preview(showSystemUi = true)
//@Composable
//private fun MainScreenContentPreview() {
//    MainScreen()
//}