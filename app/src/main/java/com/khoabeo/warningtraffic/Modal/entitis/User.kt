package com.khoabeo.warningtraffic.Modal.entitis

import com.google.firebase.database.IgnoreExtraProperties
import java.util.Date
import java.util.UUID
@IgnoreExtraProperties
data class User(
    val id: String?,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String,
    val password: String,
    val pointReward: Double? = null,
    val phone: String? = null,
    val profilePicture: String? = null,
    val createdAt: Date,
    val updatedAt: Date? = null
)