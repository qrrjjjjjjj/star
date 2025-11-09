package com.lyciv.star.utils

import androidx.compose.ui.graphics.Color
import java.text.SimpleDateFormat
import java.util.*

object TimeUtils {

    fun getCurrentTime(): String {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format(Date())
    }

    fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault())
        return sdf.format(Date())
    }

    fun getClockColor(): Color {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return when (hour) {
            in 5..11 -> Color(0xFFFFA726)
            in 12..16 -> Color(0xFFFFEB3B)
            in 17..19 -> Color(0xFFFF7043)
            else -> Color(0xFF42A5F5)
        }
    }
}
