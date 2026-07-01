package com.cropcare.core.ui.charts

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun WateringHeatmap(
    days: Int,
    wateredDays: Set<Int>,
    modifier: Modifier = Modifier,
    activeColor: Color = MaterialTheme.colorScheme.primary,
    emptyColor: Color = MaterialTheme.colorScheme.surfaceContainerHigh
) {
    val columns = 7
    val rows = (days + columns - 1) / columns
    val cellSize = 14.dp
    val gap = 4.dp

    Column(modifier = modifier) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height((cellSize + gap) * rows)
        ) {
            val cellPx = cellSize.toPx()
            val gapPx = gap.toPx()
            for (dayIndex in 0 until days) {
                val row = dayIndex / columns
                val col = dayIndex % columns
                val x = col * (cellPx + gapPx)
                val y = row * (cellPx + gapPx)
                val intensity = if (wateredDays.contains(dayIndex)) 1f else 0.15f
                val color = if (wateredDays.contains(dayIndex)) {
                    activeColor.copy(alpha = 0.4f + intensity * 0.6f)
                } else {
                    emptyColor
                }
                drawRoundRect(
                    color = color,
                    topLeft = Offset(x, y),
                    size = Size(cellPx, cellPx),
                    cornerRadius = CornerRadius(3f, 3f)
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Hace 30 días", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("Hoy", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

