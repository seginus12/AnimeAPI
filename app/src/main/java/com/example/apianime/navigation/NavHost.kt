package com.example.apianime.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.apianime.models.Shared
import com.example.apianime.screens.*


sealed class NavRoute(val route: String){
    object AnimeTopScreen: NavRoute("anime_top")
    object AnimeDetailScreen: NavRoute("anime_detail")
}

@Composable
fun NavHost(){
    val navController = rememberNavController()
    val shared: Shared = viewModel()
    NavHost(navController = navController, startDestination = NavRoute.AnimeTopScreen.route)
    {
        composable(NavRoute.AnimeTopScreen.route)
        {
            AnimeTop(navController, shared)
        }
        composable(NavRoute.AnimeDetailScreen.route)
        {
            AnimeDetail(navController, shared)
        }
    }
}