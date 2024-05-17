package com.example.mylotteryapp.di

import android.content.Context
import com.example.mylotteryapp.data.RealmRepositoryImpl
import com.example.mylotteryapp.data.ScannerRepositoryImpl
import com.example.mylotteryapp.domain.RealmRepository
import com.example.mylotteryapp.domain.ScannerRepository
import com.example.mylotteryapp.models.Boleto
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration


interface AppModule {
    val option: GmsBarcodeScannerOptions
    val scanner: GmsBarcodeScanner
    val scannerRepository: ScannerRepository
    val realmConfig: RealmConfiguration
    val realm: Realm
    val realmRepository: RealmRepository

}

class AppmoduleImpl(
    private val context: Context
) : AppModule {
    // Barcode Scanner
    override val option: GmsBarcodeScannerOptions = GmsBarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
        .build()

    override val scanner: GmsBarcodeScanner by lazy {
        GmsBarcodeScanning.getClient(context, option)

    }

    override val scannerRepository: ScannerRepository by lazy {
        ScannerRepositoryImpl(scanner, context)
    }

    // Realm
    override val realmConfig: RealmConfiguration by lazy {
        RealmConfiguration.create(
            schema = setOf(
                Boleto::class
            )
        )
    }

    override val realm: Realm by lazy {
        Realm.open(realmConfig)

    }

    override val realmRepository: RealmRepository by lazy {
        RealmRepositoryImpl(realm)
    }

}
