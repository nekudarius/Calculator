package com.vanhan.mobileapps.calculator.domain.math

enum class CalculatorOperator(
    val symbol: Char,
    val precedence: Int,
    val associativity: Associativity
) {
    PLUS('+', 1, Associativity.LEFT),
    MINUS('-', 1, Associativity.LEFT),
    TIMES('*', 2, Associativity.LEFT),
    DIVIDE('/', 2, Associativity.LEFT);

    companion object {
        fun fromChar(ch: Char): CalculatorOperator? = when (ch) {
            '+' -> PLUS
            '-' -> MINUS
            '*' -> TIMES
            '/' -> DIVIDE
            else -> null
        }
    }

}

enum class Associativity { LEFT, RIGHT}