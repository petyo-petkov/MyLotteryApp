package com.example.mylotteryapp.di

import com.example.mylotteryapp.data.RealmRepositoryImpl
import com.example.mylotteryapp.domain.RealmRepository
import com.example.mylotteryapp.models.Boleto
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RealmModule {

    @Provides
    @Singleton
    fun providesRealmConfig(): RealmConfiguration {
        return RealmConfiguration.create(
            schema = setOf(
                Boleto::class
            )
        )
    }

    @Provides
    @Singleton
    fun providesRealm(): Realm {
        return Realm.open(providesRealmConfig())
    }

    @Provides
    @Singleton
    fun providesRealmRepository(realm: Realm): RealmRepository {
        return RealmRepositoryImpl(realm)
    }

}