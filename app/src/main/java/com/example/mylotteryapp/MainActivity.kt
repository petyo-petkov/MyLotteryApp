package com.example.mylotteryapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mylotteryapp.presentation.viewModelFactory
import com.example.mylotteryapp.screens.homeScreen.HomeScreen
import com.example.mylotteryapp.ui.theme.MyLotteryAppTheme
import com.example.mylotteryapp.viewModels.RealmViewModel
import com.example.mylotteryapp.viewModels.ScannerViewModel
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.generated.NavGraphs
import com.ramcosta.composedestinations.generated.destinations.BoletosListScreenDestination
import com.ramcosta.composedestinations.generated.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.dependency

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyLotteryAppTheme(darkTheme = false) {

                val scannerViewModel = viewModel<ScannerViewModel>(
                    factory = viewModelFactory {
                        ScannerViewModel(
                            MyApp.appModule.scannerRepository,
                            MyApp.appModule.realmRepository,
                            MyApp.appModule.realm,
                        )
                    }
                )
                val realmViewModel = viewModel<RealmViewModel>(
                    factory = viewModelFactory {
                        RealmViewModel(
                            MyApp.appModule.realmRepository
                        )
                    }
                )


                DestinationsNavHost(
                    navGraph = NavGraphs.root,
                    dependenciesContainerBuilder = {
                        dependency(scannerViewModel)
                        dependency(realmViewModel)

                    }

                )

            }
        }
    }
}







