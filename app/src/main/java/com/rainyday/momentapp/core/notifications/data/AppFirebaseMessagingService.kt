package com.rainyday.momentapp.core.notifications.data

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.rainyday.momentapp.MainActivity
import com.rainyday.momentapp.NOTIFICATION_CHANNEL_DEFAULT
import com.rainyday.momentapp.R
import com.rainyday.momentapp.core.notifications.domain.model.NotificationPayload
import com.rainyday.momentapp.core.notifications.domain.usecases.SaveFCMTokenUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AppFirebaseMessagingService: FirebaseMessagingService() {
    @Inject lateinit var saveFCMTokenUseCase: SaveFCMTokenUseCase

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        CoroutineScope(Dispatchers.IO).launch {
            saveFCMTokenUseCase(token)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val title = message.notification?.title
        val description = message.notification?.body
        val payload = NotificationPayload(
            screen = message.data[SCREEN_DATA],
            itemId = message.data[ITEM_ID_DATA]
        )

        showNotification(title, description, payload)
    }

    private fun showNotification(title: String?, description: String?, payload: NotificationPayload) {
        val mainActivityIntent = Intent(this, MainActivity::class.java).apply{
            putExtra(PAYLOAD_DATA, payload)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            mainActivityIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val channelId = NOTIFICATION_CHANNEL_DEFAULT
        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title ?: "Пустий заголовок")
            .setContentText(description ?: "Пустий опис")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(System.currentTimeMillis().toInt(), notification)
    }

    companion object {
        const val SCREEN_DATA = "screen"
        const val ITEM_ID_DATA = "item_id"
        const val PAYLOAD_DATA = "payload"
    }
}