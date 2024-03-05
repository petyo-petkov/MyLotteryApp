package com.example.mylotteryapp.domain

import android.content.Context
import kotlinx.coroutines.flow.Flow

interface ScannerRepository {
    fun startScanning(): Flow<String?>
}