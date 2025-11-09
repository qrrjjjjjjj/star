package com.lyciv.star.ui.components

import android.os.Build
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lyciv.star.utils.TimeUtils
import kotlinx.coroutines.delay

@Composable
fun ClockPanel(modifier: Modifier = Modifier) {
    var currentTime by remember { mutableStateOf(TimeUtils.getCurrentTime()) }
    val currentDate = remember { TimeUtils.getCurrentDate() }
    val clockColor by animateColorAsState(
        targetValue = TimeUtils.getClockColor(),
        label = "clock_color"
    )

    LaunchedEffect(Unit) {
        while (true) {
            currentTime = TimeUtils.getCurrentTime()
            delay(1000)
        }
    }

    Box(
        modifier = modifier
            .padding(16.dp)
            .then(
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    Modifier.blur(16.dp)
                } else {
                    Modifier
                }
            )
            .background(
                Color.White.copy(alpha = 0.1f),
                RoundedCornerShape(24.dp)
            )
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = currentTime,
                style = MaterialTheme.typography.displayLarge,
                color = clockColor
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = currentDate,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White.copy(alpha = 0.8f)
            )
        }
    }
}
