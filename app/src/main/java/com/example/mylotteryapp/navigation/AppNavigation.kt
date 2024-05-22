package com.example.mylotteryapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mylotteryapp.screens.BoletosByDates
import com.example.mylotteryapp.screens.homeScreen.HomeScreen
import com.example.mylotteryapp.screens.listScreen.BoletosListScreen
import com.example.mylotteryapp.viewModels.RealmViewModel
import com.example.mylotteryapp.viewModels.ScannerViewModel
import kotlinx.serialization.Serializable


@Composable
fun AppNavigation(

) {
    val realmViewModel: RealmViewModel = hiltViewModel()
    val scannerViewModel: ScannerViewModel = hiltViewModel()
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = FirstScreen
    ) {
        composable<FirstScreen>() {

            HomeScreen(navController, realmViewModel, scannerViewModel)
        }
        composable<SecondScreen>() {

            BoletosListScreen(navController, realmViewModel, scannerViewModel)
        }
        composable<BoletosByDatesScreen>() {

            BoletosByDates(navController, realmViewModel)

        }

    }


}

@Serializable
object FirstScreen

@Serializable
object SecondScreen

@Serializable
object BoletosByDatesScreen