package com.vanhan.mobileapps.calculator.data.math

import com.vanhan.mobileapps.calculator.domain.math.CalculatorOperator
import com.vanhan.mobileapps.calculator.domain.math.CalculatorToken
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

object RpnEvaluator {
    private val mc = MathContext(16, RoundingMode.HALF_UP)

    fun evaluate(rpn: List<CalculatorToken>): BigDecimal {
        val st = ArrayDeque<BigDecimal>()
        for (t in rpn) {
            when (t) {
                is CalculatorToken.Number -> st.addLast(toBD(t.text))
                is CalculatorToken.Op -> {
                    require(st.size >= 2) { "Invalid expression" }
                    val b = st.removeLast()
                    val a = st.removeLast()
                    val res = when (t.opType) {
                        CalculatorOperator.PLUS -> a.add(b, mc)
                        CalculatorOperator.MINUS -> a.subtract(b, mc)
                        CalculatorOperator.TIMES -> a.multiply(b, mc)
                        CalculatorOperator.DIVIDE -> {
                            require(b.compareTo(BigDecimal.ZERO) != 0) { "Divide by zero" }
                            a.divide(b, mc)
                        }
                    }
                    st.addLast(res)
                }

            }
        }
        require(st.size == 1) { "Invalid result"}
        return st.last()
    }

    private fun toBD(s: String): BigDecimal =
        s.toBigDecimalOrNull() ?: error("Invalid number: $s")

}