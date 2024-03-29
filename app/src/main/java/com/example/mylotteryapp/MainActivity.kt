package com.example.mylotteryapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mylotteryapp.presentation.viewModelFactory
import com.example.mylotteryapp.screens.firstScreen.FirstMainActivity
import com.example.mylotteryapp.ui.theme.MyLotteryAppTheme
import com.example.mylotteryapp.viewModels.RealmViewModel
import com.example.mylotteryapp.viewModels.ScannerViewModel

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

                FirstMainActivity(realmViewModel, scannerViewModel)

            }
        }
    }
}







