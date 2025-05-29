package com.project.phistingdetection.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.phistingdetection.ui.theme.background
import com.project.phistingdetection.ui.theme.black
import com.project.phistingdetection.ui.theme.blue
import com.project.phistingdetection.ui.theme.poppinsFontFamily
import com.project.phistingdetection.ui.theme.white

@Composable
fun cardTipsItem(
    iconImage: Painter,
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Surface(
        color = Color.White,
        shadowElevation = 10.dp,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Image(
//                painter = rememberAsyncImagePainter(iconImage),
                painter = iconImage,
                contentScale = ContentScale.Fit,
                contentDescription = title,
                modifier = Modifier
                    .size(60.dp)
            )
            Column(
                modifier = modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = poppinsFontFamily,
                    color = black,
                )
                Text(
                    text = description,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = poppinsFontFamily,
                    color = black,
                    modifier = Modifier.padding(top = 5.dp)
                )
            }
        }
    }
}

//@Preview
//@Composable
//private fun prevCardTipsItem() {
//    cardTipsItem(
//        iconImage = painterResource(R.drawable.ic_spy),
//        title = "Tips Title",
//        description = "This is a description of the tips.",
//        modifier = Modifier.padding(16.dp)
//    )
//}