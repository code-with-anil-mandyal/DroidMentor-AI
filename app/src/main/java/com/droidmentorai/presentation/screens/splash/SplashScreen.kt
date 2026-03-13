package com.droidmentorai.presentation.screens.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droidmentorai.R
import com.droidmentorai.presentation.ui.theme.AppBackGround
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onTimeout: () -> Unit){
    LaunchedEffect(Unit) {
        delay(3000)
        onTimeout()
    }



    Box(
        modifier = Modifier
            .fillMaxSize().
        background(AppBackGround),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.droidmentor_ai),
            style = TextStyle(
                fontFamily = FontFamily( Font(R.font.fredoka_bold)),
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 45.sp
            )
        )
    }
}