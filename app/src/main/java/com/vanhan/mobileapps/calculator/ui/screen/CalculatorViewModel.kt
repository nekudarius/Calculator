package com.vanhan.mobileapps.calculator.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanhan.mobileapps.calculator.domain.math.MathEngine
import com.vanhan.mobileapps.calculator.domain.model.CalculatorState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CalculatorViewModel(
    private val engine: MathEngine
) : ViewModel() {

    private val _uiState = MutableStateFlow(CalculatorState())
    val uiState: StateFlow<CalculatorState> = _uiState.asStateFlow()

    private val operatorChars = setOf('+', '−', '×', '÷')

    // ============ Intent Handlers ============ //
    fun onNumberClick(digit: Char) = update { state ->
        state.copy(expression = state.expression + digit, error = null)
    }

    fun onDotClick() = update { state ->
        val lastOperand = state.expression.split('+', '−', '×', '÷').lastOrNull().orEmpty()
        if (lastOperand.contains('.')) state
        else state.copy(expression = state.expression + if (lastOperand.isEmpty()) "0." else ".", error = null)
    }

    fun onOperatorClick(op: Char) = update { state ->
        if (state.expression.isEmpty()) state
        else {
            val last = state.expression.last()
            if (last in operatorChars) state.copy(expression = state.expression.dropLast(1) + op, error = null)
            else state.copy(expression = state.expression + op, error = null)
        }
    }

    fun onDelete() = update { state ->
        if (state.expression.isNotEmpty()) state.copy(expression = state.expression.dropLast(1))
        else state
    }

    fun onClear() = update { CalculatorState() }

    fun onEvaluate() = update { s ->
        if (s.expression.isBlank()) s
        else try {
            val value = engine.evaluate(s.expression)
            s.copy(result = value, error = null)
        } catch (e: ArithmeticException) {
            s.copy(error = "Cannot divide by 0")
        } catch (e: Exception) {
            s.copy(error = "Input not valid")
        }
    }

    // ============ Helper ============ //
    private fun update(block: (CalculatorState) -> CalculatorState) {
        viewModelScope.launch {
            _uiState.value = block(_uiState.value)
        }
    }

}