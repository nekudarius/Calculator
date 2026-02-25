package com.vanhan.mobileapps.calculator.domain.math

interface MathEngine {

    @Throws(IllegalStateException::class, ArithmeticException::class)
    fun evaluate(expressionUiSymbols: String): String

    fun applyPercentToLastOperand(expressionUiSymbols: String): String
}