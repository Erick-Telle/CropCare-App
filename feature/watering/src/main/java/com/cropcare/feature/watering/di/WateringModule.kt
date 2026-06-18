package com.cropcare.feature.watering.di

import com.cropcare.core.domain.event.WateringEventEmitter
import com.cropcare.feature.watering.event.WateringEventEmitterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class WateringModule {

    @Binds
    @Singleton
    abstract fun bindWateringEventEmitter(impl: WateringEventEmitterImpl): WateringEventEmitter
}
