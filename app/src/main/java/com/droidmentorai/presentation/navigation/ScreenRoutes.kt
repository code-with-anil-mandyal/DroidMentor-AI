package com.droidmentorai.presentation.navigation

sealed class ScreenRoutes(val route : String) {
    object SplashScreen : ScreenRoutes("splash_screen")
    object HomeScreen : ScreenRoutes("home_screen")
    object AiChatScreen : ScreenRoutes("ai_chat_screen")
    object HistoryScreen : ScreenRoutes("history_screen")
}