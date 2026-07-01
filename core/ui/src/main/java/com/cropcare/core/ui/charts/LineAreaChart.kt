package com.cropcare.core.ui.charts

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

data class LineChartPoint(
    val label: String,
    val value: Float
)

@Composable
fun LineAreaChart(
    points: List<LineChartPoint>,
    modifier: Modifier = Modifier,
    lineColor: Color = MaterialTheme.colorScheme.primary
) {
    if (points.isEmpty()) return

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp)
    ) {
        val maxValue = points.maxOf { it.value }.coerceAtLeast(1f)
        val stepX = size.width / (points.size - 1).coerceAtLeast(1)
        val path = Path()
        val fillPath = Path()

        points.forEachIndexed { index, point ->
            val x = index * stepX
            val y = size.height - (point.value / maxValue) * size.height * 0.9f
            if (index == 0) {
                path.moveTo(x, y)
                fillPath.moveTo(x, size.height)
                fillPath.lineTo(x, y)
            } else {
                path.lineTo(x, y)
                fillPath.lineTo(x, y)
            }
        }
        fillPath.lineTo((points.size - 1) * stepX, size.height)
        fillPath.close()

        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(lineColor.copy(alpha = 0.35f), Color.Transparent),
                startY = 0f,
                endY = size.height
            )
        )
        drawPath(
            path = path,
            color = lineColor,
            style = Stroke(width = 3f)
        )
        points.forEachIndexed { index, point ->
            val x = index * stepX
            val y = size.height - (point.value / maxValue) * size.height * 0.9f
            drawCircle(color = lineColor, radius = 6f, center = Offset(x, y))
            drawCircle(color = Color.White.copy(alpha = 0.9f), radius = 3f, center = Offset(x, y))
        }
    }
}
