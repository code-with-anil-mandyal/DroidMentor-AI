package com.droidmentorai.presentation.components

import androidx.compose.ui.graphics.Color

data class Menu(
    val id : Int,
    val title : String,
    val color : Color
)

val menuList = listOf(
    Menu(1, "Ask", Color(0xFFF8BBAE)),
    Menu(2, "Generate Code", Color(0xFFB6FFB0)),
    Menu(3, "Explain Code", Color(0xFFFFDEFB)),
    Menu(4, "Java to Kotlin", Color(0xFFDEE7FF))
)