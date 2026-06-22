package com.cropcare.core.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cropcare.core.domain.model.CareAdviceLevel

@Composable
fun CareAdviceCard(
    icon: ImageVector,
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    level: CareAdviceLevel? = null,
    levelIcons: List<ImageVector>? = null
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                shape = RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(28.dp)
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            if (level != null && levelIcons != null) {
                Spacer(modifier = Modifier.height(12.dp))
                LevelIndicator(level = level, icons = levelIcons)
            }
        }
    }
}

@Composable
private fun LevelIndicator(
    level: CareAdviceLevel,
    icons: List<ImageVector>
) {
    val activeCount = when (level) {
        CareAdviceLevel.BAJA -> 1
        CareAdviceLevel.MEDIA -> 2
        CareAdviceLevel.ALTA -> 3
    }
    val levelLabel = when (level) {
        CareAdviceLevel.BAJA -> "Baja"
        CareAdviceLevel.MEDIA -> "Media"
        CareAdviceLevel.ALTA -> "Alta"
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        icons.forEachIndexed { index, icon ->
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (index < activeCount) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.25f)
                },
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = levelLabel,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
