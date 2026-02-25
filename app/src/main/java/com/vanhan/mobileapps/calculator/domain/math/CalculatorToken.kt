package com.vanhan.mobileapps.calculator.domain.math

sealed class CalculatorToken {
    data class Number(val text: String) : CalculatorToken()
    data class Op(val opType: CalculatorOperator) : CalculatorToken()
}