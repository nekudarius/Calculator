package com.vanhan.mobileapps.calculator.domain.math


class FakeMathEngine : MathEngine {
    var result: String = "0"
    var throwException: Exception? = null

    override fun evaluate(expressionUiSymbols: String): String {
        throwException?.let { throw it }
        return result
    }

    override fun applyPercentToLastOperand(expressionUiSymbols: String): String {
        TODO("Not yet implemented")
    }
}