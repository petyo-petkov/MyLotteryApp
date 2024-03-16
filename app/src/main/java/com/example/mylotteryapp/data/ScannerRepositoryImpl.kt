package com.example.mylotteryapp.data

import android.content.Context
import android.widget.Toast
import com.example.mylotteryapp.domain.ScannerRepository
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class ScannerRepositoryImpl(
    private val scanner: GmsBarcodeScanner,
    private val context: Context

): ScannerRepository {

    override fun startScanning(): Flow<String?> {
        return callbackFlow {
            scanner.startScan()

                .addOnSuccessListener { barcode ->
                    launch {
                        send(barcode.rawValue)
                    }
                }
                .addOnCanceledListener { }
                .addOnFailureListener {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    Toast.makeText(context, "Escaneado fallido", Toast.LENGTH_LONG)
                        .show()
                }
            awaitClose { }
        }
    }
}