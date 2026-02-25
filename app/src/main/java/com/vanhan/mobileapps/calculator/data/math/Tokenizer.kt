package com.vanhan.mobileapps.calculator.data.math

import com.vanhan.mobileapps.calculator.domain.math.CalculatorOperator
import com.vanhan.mobileapps.calculator.domain.math.CalculatorToken

object Tokenizer {
    fun tokenize(expression: String): List<CalculatorToken> {
        val output = mutableListOf<CalculatorToken>()
        val stringBuilder = StringBuilder()

        fun flushNumber() {
            if (stringBuilder.isNotEmpty()) {
                val text = stringBuilder.toString()
                require(isValidNumber(text)) { "Invalid number: $text" }
                output += CalculatorToken.Number(text)
                stringBuilder.clear()
            }
        }

        for (ch in expression) {
            when {
                ch.isDigit() || ch == '.' -> stringBuilder.append(ch)
                CalculatorOperator.fromChar(ch) != null -> {
                    flushNumber()
                    output += CalculatorToken.Op(CalculatorOperator.fromChar(ch)!!)
                }
                ch == ' ' -> {} //ignore
                else -> error("Unknown char: $ch")
            }
        }
        flushNumber()
        require(output.isNotEmpty()) { "Empty expression" }
        return output
    }

    private fun isValidNumber(s: String): Boolean {
        // simple: can't be "..", can't just be "."
        if (s.count { it == '.' } > 1) return false
        if (s == ".") return false
        return true
    }
}