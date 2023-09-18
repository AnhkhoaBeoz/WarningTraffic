package com.khoabeo.doannghanh.Modal.entitis

import java.util.Date

data class LearnDrivingQuestion(
    val id: Int?,
    val learnDrivingId: Int,
    val question: String,
    val answer: String,
    val createdAt: Date,
    val updatedAt: Date
)
