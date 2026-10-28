package com.vanhan.mobileapps.calculator.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.vanhan.mobileapps.calculator.DevicePreviews
import com.vanhan.mobileapps.calculator.ui.theme.CalculatorTheme


private sealed class CalcKey(val label: String) {
    data class Number(val value: Int) : CalcKey(value.toString())
    data object Dot : CalcKey(".")
    data object Clear : CalcKey("C")
    data object Delete : CalcKey("⌫")
    data object Percent : CalcKey("%")
    data class Operator(val op: Char) : CalcKey(op.toString()) // UI symbols: +, −, ×, ÷
    data object Equals : CalcKey("=")
}


private val DefaultCalcKeys: List<CalcKey> = listOf(
    CalcKey.Delete, CalcKey.Clear, CalcKey.Percent, CalcKey.Operator('÷'),
    CalcKey.Number(7), CalcKey.Number(8), CalcKey.Number(9), CalcKey.Operator('×'),
    CalcKey.Number(4), CalcKey.Number(5), CalcKey.Number(6), CalcKey.Operator('−'),
    CalcKey.Number(1), CalcKey.Number(2), CalcKey.Number(3), CalcKey.Operator('+'),
    CalcKey.Number(0), CalcKey.Dot, CalcKey.Equals
)


private fun parseCustomKeys(keys: List<String>): List<CalcKey> =
    keys.map { s ->
        when (s) {
            "C" -> CalcKey.Clear
            "⌫" -> CalcKey.Delete
            "%" -> CalcKey.Percent
            "." -> CalcKey.Dot
            "=" -> CalcKey.Equals
            "+", "−", "×", "÷" -> CalcKey.Operator(s.first())
            else -> s.toIntOrNull()?.let { CalcKey.Number(it) }
                ?: error("keys contain invalid entry: $s")
        }
    }

@Composable
fun CalculatorKeypadUI(
    modifier: Modifier = Modifier,
    color: Color = Color(0xFF0090FF),
    keys: List<String>? = null, // optional override
    onNumberClick: (Int) -> Unit,
    onDotClick: () -> Unit,
    onClearClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onPercentClick: () -> Unit,
    onOperatorClick: (Char) -> Unit,
    onEqualClick: () -> Unit
) {
    val keyPadToShow: List<CalcKey> = keys?.let(::parseCustomKeys) ?: DefaultCalcKeys

    LazyVerticalGrid(
        modifier = modifier.fillMaxWidth(),
        columns = GridCells.Fixed(4),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(12.dp),
        userScrollEnabled = false // keypads are usually fixed height; adjust them to suit your layout.
    ) {
        items(
            count = keyPadToShow.size,
            key = { idx -> keyPadToShow[idx].label }
        ) { idx ->
            when (val key = keyPadToShow[idx]) {
                is CalcKey.Clear -> CalculatorBtn(
                    modifier = Modifier.testTag("btn_clear"),
                    label = key.label,
                    color = color,
                    variant = ButtonVariant.Function,    // styling custom
                    onClick = onClearClick,
                )
                is CalcKey.Delete -> CalculatorBtn(
                    modifier = Modifier.testTag("btn_delete"),
                    label = key.label,
                    color = color,
                    variant = ButtonVariant.Function,
                    onClick = onDeleteClick,
                )
                is CalcKey.Percent -> CalculatorBtn(
                    modifier = Modifier.testTag("btn_percent"),
                    label = key.label,
                    color = color,
                    variant = ButtonVariant.Function,
                    onClick = onPercentClick,
                )
                is CalcKey.Operator -> {
                    val tag = when (key.op) {
                        '+' -> "btn_plus"
                        '−' -> "btn_minus"
                        '×' -> "btn_multiply"
                        '÷' -> "btn_divide"
                        else -> error("Unknown operator ${key.op}")
                    }
                    CalculatorBtn(
                        modifier = Modifier.testTag(tag),
                        label = key.label,
                        color = color,
                        variant = ButtonVariant.Operator,
                        onClick = { onOperatorClick(key.op) },
                    )
                }
                is CalcKey.Equals -> CalculatorBtn(
                    modifier = Modifier.testTag("btn_equals"),
                    label = key.label,
                    color = color,
                    variant = ButtonVariant.Equals,
                    onClick = onEqualClick,
                )
                is CalcKey.Dot -> CalculatorBtn(
                    modifier = Modifier.testTag("btn_dot"),
                    label = key.label,
                    color = color,
                    variant = ButtonVariant.Number,
                    onClick = onDotClick,
                )
                is CalcKey.Number -> CalculatorBtn(
                    modifier = Modifier.testTag("btn_${key.value}"),
                    label = key.label,
                    color = color,
                    variant = ButtonVariant.Number,
                    onClick = { onNumberClick(key.value) },
                )
            }
        }
    }
}


@DevicePreviews
@Composable
fun CalculatorKeypadPreview() {
    CalculatorTheme {
        CalculatorKeypadUI(
            onNumberClick = {},
            onDotClick = {},
            onClearClick = {},
            onDeleteClick = {},
            onPercentClick = {},
            onOperatorClick = {},
            onEqualClick = {}
        )
    }
}