package com.vanhan.mobileapps.calculator.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vanhan.mobileapps.calculator.DevicePreviews
import com.vanhan.mobileapps.calculator.ui.theme.CalculatorTheme

enum class ButtonVariant { Number, Operator, Function, Equals }

@Composable
fun CalculatorBtn(
    modifier: Modifier = Modifier,
    label: String,
    color: Color = Color(0xFF18A7A7),
    variant: ButtonVariant = ButtonVariant.Number,
    onClick: () -> Unit,
) {
    val (containerColor, contentColor) = when (variant) {
        ButtonVariant.Number ->
            MaterialTheme.colorScheme.secondaryContainer to MaterialTheme.colorScheme.onSecondaryContainer
        ButtonVariant.Operator ->
            MaterialTheme.colorScheme.tertiaryContainer to MaterialTheme.colorScheme.onTertiaryContainer
        ButtonVariant.Function ->
            MaterialTheme.colorScheme.surfaceVariant to MaterialTheme.colorScheme.onSurfaceVariant
        ButtonVariant.Equals ->
            color to MaterialTheme.colorScheme.onPrimary
    }

    Button(
        onClick = onClick,
        modifier = modifier.defaultMinSize(64.dp, 64.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        contentPadding = PaddingValues(8.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Medium
        )
    }
}

@DevicePreviews
@Composable
fun CalculatorBtnPreview() {
    CalculatorTheme {
        Column {
            CalculatorBtn(
                label = "-"
            ) { }
        }
    }
}