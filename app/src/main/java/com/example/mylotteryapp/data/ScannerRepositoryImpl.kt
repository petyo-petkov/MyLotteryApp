package com.example.mylotteryapp.data

import android.util.Log
import com.example.mylotteryapp.domain.ScannerRepository
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ScannerRepositoryImpl @Inject constructor(
    private val scanner: GmsBarcodeScanner
) : ScannerRepository {
    override fun startScanning(): Flow<String?> {
        return callbackFlow {
            scanner.startScan()
                .addOnSuccessListener { barcode ->
                    launch {
                        send(barcode.rawValue)

                    }
                }
                .addOnCanceledListener {
                    Log.i("TAG", "startScanning: canceled")

                }
                .addOnFailureListener {
                    Log.e("TAG", "startScanning: failed")
                }
            awaitClose { }

        }
    }

}