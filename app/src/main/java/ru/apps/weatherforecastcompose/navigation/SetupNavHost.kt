package ru.apps.weatherforecastcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.apps.weatherforecastcompose.MainActivity
import ru.apps.weatherforecastcompose.screens.MainCard
import ru.apps.weatherforecastcompose.screens.SplashScreen
import ru.apps.weatherforecastcompose.utils.Constants

sealed class Screens(val route: String){
    object Splash: Screens(route = Constants.Screens.SPLASH_SCREEN)
    object Main: Screens(route = Constants.Screens.MAIN_SCREEN)

}

@Composable
fun SetupNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.Splash.route
    ) {
        composable(route = Screens.Splash.route) {
            SplashScreen(navController = navController)

        }
        composable(route = Screens.Main.route) {
            MainActivity()
        }

    }
}