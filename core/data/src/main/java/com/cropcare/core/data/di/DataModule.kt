package com.cropcare.core.data.di

import android.content.Context
import androidx.room.Room
import com.cropcare.core.data.local.CropCareDatabase
import com.cropcare.core.data.local.dao.ClimateDao
import com.cropcare.core.data.local.dao.PlantDao
import com.cropcare.core.data.local.dao.SpeciesDao
import com.cropcare.core.data.local.dao.WateringDao
import com.cropcare.core.data.repository.ClimateRepositoryImpl
import com.cropcare.core.data.repository.OnboardingRepositoryImpl
import com.cropcare.core.data.repository.PlantRepositoryImpl
import com.cropcare.core.data.repository.SpeciesRepositoryImpl
import com.cropcare.core.data.repository.WateringRepositoryImpl
import com.cropcare.core.domain.repository.ClimateRepository
import com.cropcare.core.domain.repository.OnboardingRepository
import com.cropcare.core.domain.repository.PlantRepository
import com.cropcare.core.domain.repository.SpeciesCatalogSyncObserver
import com.cropcare.core.domain.repository.SpeciesRepository
import com.cropcare.core.domain.repository.WateringRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CropCareDatabase =
        Room.databaseBuilder(
            context,
            CropCareDatabase::class.java,
            CropCareDatabase.DATABASE_NAME
        )
            .addCallback(CropCareDatabase.callback)
            .build()

    @Provides
    fun providePlantDao(database: CropCareDatabase): PlantDao = database.plantDao()

    @Provides
    fun provideSpeciesDao(database: CropCareDatabase): SpeciesDao = database.speciesDao()

    @Provides
    fun provideWateringDao(database: CropCareDatabase): WateringDao = database.wateringDao()

    @Provides
    fun provideClimateDao(database: CropCareDatabase): ClimateDao = database.climateDao()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPlantRepository(impl: PlantRepositoryImpl): PlantRepository

    @Binds
    @Singleton
    abstract fun bindSpeciesRepository(impl: SpeciesRepositoryImpl): SpeciesRepository

    @Binds
    @Singleton
    abstract fun bindSpeciesCatalogSyncObserver(
        impl: SpeciesRepositoryImpl
    ): SpeciesCatalogSyncObserver

    @Binds
    @Singleton
    abstract fun bindWateringRepository(impl: WateringRepositoryImpl): WateringRepository

    @Binds
    @Singleton
    abstract fun bindClimateRepository(impl: ClimateRepositoryImpl): ClimateRepository

    @Binds
    @Singleton
    abstract fun bindOnboardingRepository(impl: OnboardingRepositoryImpl): OnboardingRepository
}
