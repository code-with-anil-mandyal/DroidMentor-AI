package com.droidmentorai.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.droidmentorai.core.utils.ChatMode
import com.droidmentorai.presentation.screens.aiChatScreen.AiChatScreen
import com.droidmentorai.presentation.screens.chatHistory.HistoryScreen
import com.droidmentorai.presentation.screens.home.HomeScreen
import com.droidmentorai.presentation.screens.splash.SplashScreen

@Composable
fun AppNavigation(modifier: Modifier) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ScreenRoutes.SplashScreen.route,
        modifier = modifier
    ) {

        composable(ScreenRoutes.SplashScreen.route) {
            SplashScreen {
                navController.navigate(ScreenRoutes.HomeScreen.route) {
                    popUpTo(ScreenRoutes.SplashScreen.route) {
                        inclusive = true
                    }
                }
            }
        }

        composable(ScreenRoutes.HomeScreen.route) {

            HomeScreen(
                onMenuClick = { id ->

                    val mode = when (id) {
                        1 -> ChatMode.ASK
                        2 -> ChatMode.GENERATE_CODE
                        3 -> ChatMode.EXPLAIN_CODE
                        4 -> ChatMode.JAVA_TO_KOTLIN
                        else -> ChatMode.ASK
                    }

                    navController.navigate("${ScreenRoutes.AiChatScreen.route}/${mode.name}")
                },

                onChatClick = { sessionId ->
                    navController.navigate("${ScreenRoutes.AiChatScreen.route}/history/$sessionId")
                },

                onSeeAllClick = {
                    navController.navigate(ScreenRoutes.HistoryScreen.route)
                }
            )
        }

        // NEW CHAT
        composable(
            "${ScreenRoutes.AiChatScreen.route}/{mode}",
            arguments = listOf(
                navArgument("mode") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            val modeString = backStackEntry.arguments?.getString("mode")

            val chatMode = try {
                ChatMode.valueOf(modeString ?: ChatMode.ASK.name)
            } catch (e: Exception) {
                ChatMode.ASK
            }

            AiChatScreen(
                onBackClick = { navController.popBackStack() },
                sessionId = null,
                mode = chatMode
            )
        }

        // OPEN CHAT FROM HISTORY
        composable(
            "${ScreenRoutes.AiChatScreen.route}/history/{sessionId}",
            arguments = listOf(
                navArgument("sessionId") { type = NavType.LongType }
            )
        ) { backStackEntry ->

            val sessionId =
                backStackEntry.arguments?.getLong("sessionId") ?: -1L

            AiChatScreen(
                onBackClick = { navController.popBackStack() },
                sessionId = sessionId
            )
        }

        composable(ScreenRoutes.HistoryScreen.route) {

            HistoryScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onChatClick = { sessionId ->
                    navController.navigate("${ScreenRoutes.AiChatScreen.route}/history/$sessionId")
                }
            )
        }
    }
}
