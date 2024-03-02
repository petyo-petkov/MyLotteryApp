package com.example.mylotteryapp

import android.app.Application
import com.example.mylotteryapp.di.AppModule
import com.example.mylotteryapp.di.AppmoduleImpl
import io.realm.kotlin.Realm

class MyApp : Application() {

    companion object {
        lateinit var appModule: AppModule
        lateinit var realm: Realm
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppmoduleImpl(this)
    }

}

