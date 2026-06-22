package com.cropcare.feature.notifications.di

import android.app.AlarmManager
import android.app.NotificationManager
import android.content.Context
import com.cropcare.core.domain.repository.PlantNotificationScheduler
import com.cropcare.feature.notifications.scheduler.AlarmManagerPlantNotificationScheduler
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationsProvidesModule {

    @Provides
    @Singleton
    fun provideNotificationManager(@ApplicationContext context: Context): NotificationManager =
        context.getSystemService(NotificationManager::class.java)

    @Provides
    @Singleton
    fun provideAlarmManager(@ApplicationContext context: Context): AlarmManager =
        context.getSystemService(AlarmManager::class.java)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class NotificationsBindsModule {

    @Binds
    @Singleton
    abstract fun bindPlantNotificationScheduler(
        impl: AlarmManagerPlantNotificationScheduler
    ): PlantNotificationScheduler
}
