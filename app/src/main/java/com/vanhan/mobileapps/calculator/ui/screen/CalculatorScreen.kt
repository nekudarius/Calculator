package com.vanhan.mobileapps.calculator.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vanhan.mobileapps.calculator.DevicePreviews
import com.vanhan.mobileapps.calculator.data.math.MathEngineImpl
import com.vanhan.mobileapps.calculator.ui.component.CalculatorKeypadUI
import com.vanhan.mobileapps.calculator.ui.theme.CalculatorTheme

@Composable
fun CalculatorScreen(
    modifier: Modifier = Modifier,
    vm: CalculatorViewModel = viewModel()
) {
    val state by vm.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
    ) {
        // ======== Display (Expression + Result / Error) ========
        DisplaySection(
            expression = state.expression,
            result = state.result,
            error = state.error
        )

        Spacer(Modifier.height(12.dp))

        // ======== Keypad ========
        CalculatorKeypadUI(
            onNumberClick = { d -> vm.onNumberClick(d.digitToChar()) },
            onDotClick = vm::onDotClick,
            onClearClick = vm::onClear,
            onDeleteClick = vm::onDelete,
            onPercentClick = { },
            onOperatorClick = vm::onOperatorClick, // send '÷', '×', '−', '+'
            onEqualClick = vm::onEvaluate
        )
    }
}

@Composable
private fun DisplaySection(
    expression: String,
    result: String,
    error: String?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, bottom = 24.dp),
        horizontalAlignment = Alignment.End
    ) {
        // Expression (Small)
        Text(
            modifier = Modifier.testTag("txt_expression"),
            text = expression.ifBlank { "0" },
            style = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.height(8.dp))
        // Result / Error (Large)
        Text(
            modifier = Modifier.testTag("txt_result"),
            text = error ?: result,
            style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
            color = if (error != null) MaterialTheme.colorScheme.error
            else MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

private fun Int.digitToChar(): Char = ('0'.code + this).toChar()

@DevicePreviews
@Composable
fun CalculatorScreenPreview() {
    CalculatorTheme {
        Scaffold { paddingValues ->
            val vm = remember {
                CalculatorViewModel(
                    MathEngineImpl()
                )
            }
            CalculatorScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                vm = vm
            )
        }
    }
}