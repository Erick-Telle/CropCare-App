package com.cropcare.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cropcare.core.domain.model.WateringStatus
import com.cropcare.core.ui.theme.StatusOk
import com.cropcare.core.ui.theme.StatusOverdue
import com.cropcare.core.ui.theme.StatusPending

@Composable
fun StatusChip(
    status: WateringStatus,
    modifier: Modifier = Modifier
) {
    val (label, containerColor, labelColor) = when (status) {
        WateringStatus.AL_DIA -> Triple("Al día", StatusOk.copy(alpha = 0.18f), StatusOk)
        WateringStatus.PENDIENTE -> Triple("Pendiente", StatusPending.copy(alpha = 0.18f), StatusPending)
        WateringStatus.ATRASADA -> Triple("Atrasada", StatusOverdue.copy(alpha = 0.18f), StatusOverdue)
    }

    Text(
        text = label,
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(containerColor)
            .padding(horizontal = 10.dp, vertical = 4.dp),
        style = MaterialTheme.typography.labelMedium,
        fontWeight = FontWeight.SemiBold,
        color = labelColor
    )
}
