package com.mobile.discernopet

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mobile.discernopet.ui.screens.HomeScreen
import com.mobile.discernopet.ui.screens.LoginPage
import com.mobile.discernopet.ui.screens.SignUpPage


@Composable
fun MyAppNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "signup", builder = {
        composable("signup") {
            SignUpPage(modifier, navController, authViewModel)
        }
        composable("login") {
            LoginPage(modifier, navController, authViewModel)
        }
        composable("home") {
            HomeScreen(modifier, navController, authViewModel)

        }
    })
}