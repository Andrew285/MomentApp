package com.rainyday.momentapp.core.notifications.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NotificationPayload(
    val screen: String?,
    val itemId: String?,
) : Parcelable