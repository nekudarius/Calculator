package com.vanhan.mobileapps.calculator.domain.model

data class CalculatorState(
    val expression: String = "",
    val result: String = "0",
    val error: String? = null
)