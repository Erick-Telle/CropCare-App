package com.cropcare.feature.settings.di

import com.cropcare.feature.settings.data.SettingsPreferencesRepository
import com.cropcare.feature.settings.data.SettingsPreferencesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsModule {

    @Binds
    @Singleton
    abstract fun bindSettingsPreferencesRepository(
        impl: SettingsPreferencesRepositoryImpl
    ): SettingsPreferencesRepository
}
