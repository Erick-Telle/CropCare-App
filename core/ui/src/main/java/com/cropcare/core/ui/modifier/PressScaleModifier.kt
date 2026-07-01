package com.cropcare.core.ui.modifier

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale

fun Modifier.pressScale(
    interactionSource: MutableInteractionSource = MutableInteractionSource(),
    scaleDown: Float = 0.96f
): Modifier = composed {
    val source = remember { interactionSource }
    val isPressed by source.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) scaleDown else 1f,
        animationSpec = spring(stiffness = 400f),
        label = "pressScale"
    )
    scale(scale)
}
