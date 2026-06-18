package com.cropcare.feature.watering.quickwater

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.cropcare.core.ui.components.InputField
import com.cropcare.core.ui.components.PrimaryButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuickWateringBottomSheet(
    plantName: String,
    suggestedWaterMl: Int,
    waterAmount: String,
    onWaterAmountChange: (String) -> Unit,
    notes: String,
    onNotesChange: (String) -> Unit,
    isSaving: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp)
        ) {
            Text(
                text = "Registrar riego",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = plantName,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Cantidad sugerida: $suggestedWaterMl ml",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(20.dp))

            InputField(
                value = waterAmount,
                onValueChange = onWaterAmountChange,
                label = "Cantidad de agua (ml)",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(12.dp))

            InputField(
                value = notes,
                onValueChange = onNotesChange,
                label = "Notas (opcional)",
                singleLine = false
            )
            Spacer(modifier = Modifier.height(24.dp))

            PrimaryButton(
                text = if (isSaving) "Guardando..." else "Confirmar riego",
                onClick = onConfirm,
                enabled = !isSaving && waterAmount.toIntOrNull()?.let { it > 0 } == true
            )
        }
    }
}
