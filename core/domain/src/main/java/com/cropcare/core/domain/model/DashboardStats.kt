package com.cropcare.core.domain.model

data class DashboardStats(
    val wateringsThisMonth: Int = 0,
    val streakDays: Int = 0,
    val completedWateringsLast7Days: List<Int> = List(7) { 0 }
)
