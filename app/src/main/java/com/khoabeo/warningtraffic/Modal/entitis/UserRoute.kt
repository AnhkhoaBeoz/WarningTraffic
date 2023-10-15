package com.khoabeo.warningtraffic.Modal.entitis

import java.util.Date

data class UserRoute(
    val userId: Int,
    val routeId: Int,
    val createdAt: Date,
    val updatedAt: Date
)
