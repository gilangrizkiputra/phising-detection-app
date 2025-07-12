package com.project.phistingdetection.NewsDetailScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.project.phistingdetection.R
import com.project.phistingdetection.data.model.NewsItem
import com.project.phistingdetection.ui.theme.black
import com.project.phistingdetection.ui.theme.poppinsFontFamily

@Composable
fun NewsDetailScreen(
    news: NewsItem,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    NewsDetailScreenContent(
        news = news,
        onBackClick = onBackClick,
        modifier = modifier
    )
}

@Composable
fun NewsDetailScreenContent(
    news: NewsItem,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        item {
            Column(
                modifier = modifier
                    .padding(16.dp)
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIos,
                        contentDescription = "Back",
                        modifier = Modifier
                            .clickable { onBackClick() }
                    )
                    Spacer(modifier = Modifier.padding(start = 8.dp))
                    Text(
                        text = "Detail Berita",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = poppinsFontFamily,
                        color = black,

                        modifier = Modifier
                    )
                }

                Spacer(modifier = Modifier.padding(top = 16.dp))

                Text(
                    text = news.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = poppinsFontFamily,
                    color = black
                )

                Spacer(modifier = Modifier.padding(top = 8.dp))

                Text(
                    text = news.source_name,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = poppinsFontFamily,
                    color = black
                )
                Spacer(modifier = Modifier.padding(top = 4.dp))
                Text(
                    text = news.pubDate,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = poppinsFontFamily,
                    color = black
                )

                Spacer(modifier = Modifier.padding(top = 16.dp))

                news.image_url?.let {
                    Image(
                        painter = rememberAsyncImagePainter(it),
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center,
                        contentDescription = "News Image",
                        modifier = modifier
                            .fillMaxSize()
                            .height(200.dp)
                            .clip(shape = RoundedCornerShape(20.dp))

                    )
                }

                Spacer(modifier = Modifier.padding(top = 8.dp))

                Text(
                    text = news.description ?: "Deskripsi tidak tersedia",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = poppinsFontFamily,
                    color = black
                )
            }
        }
    }
}

//@Preview(showSystemUi = true)
//@Composable
//private fun PreviewNewsDetailScreenContent(
//
//) {
//    NewsDetailScreen()
//}