package com.droidmentorai.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droidmentorai.presentation.ui.theme.AppBackGround

@Composable
fun Header(
    modifier: Modifier = Modifier,
    height: Dp = 56.dp,
    isShowBack: Boolean = false,
    isShowRight: Boolean = false,
    onBackClick: () -> Unit = {},
    onRightClick: () -> Unit = {},
    rightIcon: Int? = null,
    backIcon: ImageVector? = null,
    title: String = "",
    titleColor: Color = Color.Black
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 5.dp)
            .height(height),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(containerColor = AppBackGround)
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // BACK ICON
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterStart
            ) {
                if (isShowBack && backIcon != null) {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = backIcon,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                }
            }

            // TITLE
            Box(
                modifier = Modifier.weight(2f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = titleColor,
                    maxLines = 1
                )
            }

            // RIGHT ICON
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterEnd
            ) {
                if (isShowRight && rightIcon != null) {
                    IconButton(onClick = onRightClick) {
                        Icon(
                            painter = painterResource(rightIcon),
                            contentDescription = "Action",
                            tint = Color.Unspecified
                        )
                    }
                }
            }
        }
    }
}