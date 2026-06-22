package com.cropcare.feature.notifications.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.cropcare.core.domain.usecase.RegisterWateringUseCase
import com.cropcare.feature.notifications.NotificationConstants
import com.cropcare.feature.notifications.WateringNotificationHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class WaterNowReceiver : BroadcastReceiver() {

    @Inject
    lateinit var registerWateringUseCase: RegisterWateringUseCase

    @Inject
    lateinit var notificationHelper: WateringNotificationHelper

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != NotificationConstants.ACTION_WATER_NOW) return

        val plantId = intent.getLongExtra(NotificationConstants.EXTRA_PLANT_ID, -1L)
        val cantidadAguaMl = intent.getIntExtra(NotificationConstants.EXTRA_CANTIDAD_AGUA, 0)
        val notificationId = intent.getIntExtra(NotificationConstants.EXTRA_NOTIFICATION_ID, -1)

        if (plantId == -1L) return

        val pendingResult = goAsync()

        scope.launch {
            try {
                registerWateringUseCase(
                    plantId = plantId,
                    cantidadAguaMl = cantidadAguaMl,
                    completadoPorNotificacion = true
                )
                if (notificationId != -1) {
                    notificationHelper.dismissNotification(notificationId)
                }
            } finally {
                pendingResult.finish()
            }
        }
    }
}
