package com.khoabeo.doannghanh.Modal.entitis

import java.util.Date

data class User(
    val id: Int?,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val pointReward : Double,
    val phone: String,
    val profilePicture: String,
    val createdAt: Date,
    val updatedAt: Date

)