package com.cropcare.core.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
        WateringStatus.AL_DIA -> Triple("Al día", StatusOk.copy(alpha = 0.15f), StatusOk)
        WateringStatus.PENDIENTE -> Triple("Pendiente", StatusPending.copy(alpha = 0.15f), StatusPending)
        WateringStatus.ATRASADA -> Triple("Atrasada", StatusOverdue.copy(alpha = 0.15f), StatusOverdue)
    }

    AssistChip(
        onClick = {},
        modifier = modifier,
        enabled = false,
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = labelColor
            )
        },
        shape = RoundedCornerShape(8.dp),
        colors = AssistChipDefaults.assistChipColors(
            disabledContainerColor = containerColor,
            disabledLabelColor = labelColor
        ),
        border = AssistChipDefaults.assistChipBorder(
            enabled = false,
            disabledBorderColor = Color.Transparent
        )
    )
}
