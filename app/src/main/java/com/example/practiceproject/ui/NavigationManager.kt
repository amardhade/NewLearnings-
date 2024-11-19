package com.example.practiceproject.ui

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.practiceproject.MainActivity
import com.example.practiceproject.ui.screens.HomeScreen
import com.example.practiceproject.ui.screens.LoginScreen
import com.example.practiceproject.ui.screens.LoginScreenViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.java.KoinJavaComponent.inject

@Composable
fun NavigationManager(
    modifier: Modifier
) {

    val navController = rememberNavController()
    val context = LocalContext.current
//    val loginScreenViewModel = ViewModelProvider(context as MainActivity)[LoginScreenViewModel::class.java]
    val loginScreenViewModel = koinViewModel<LoginScreenViewModel>()

    NavHost(modifier = modifier, navController = navController, startDestination = LoginRoute) {

        composable<LoginRoute> {
            LoginScreen(loginScreenViewModel = loginScreenViewModel, navigateToHome = {email, pwd -> navController.navigate(HomeRoute(email, pwd))})
        }

        composable<HomeRoute> { backStackEntry ->
            val homeRoute =  backStackEntry.toRoute<HomeRoute>()
            HomeScreen(email = homeRoute.email, pwd = homeRoute.pwd)
        }

    }



}