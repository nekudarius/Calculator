package com.vanhan.mobileapps.calculator.data.math

import com.vanhan.mobileapps.calculator.domain.math.Associativity
import com.vanhan.mobileapps.calculator.domain.math.CalculatorToken

object ShuntingYard {
    fun toRpn( tokens: List<CalculatorToken>): List<CalculatorToken> {
        val output = mutableListOf<CalculatorToken>()
        val operatorStack = ArrayDeque<CalculatorToken.Op>()

        fun pushOperator(operator: CalculatorToken.Op) {
            // As long as there is an operator on the stack with higher or equal precedence (for LEFT),
            // pop it to the output.
            while (operatorStack.isNotEmpty()) {
                val top = operatorStack.last()
                val shouldPop =
                    (top.opType.precedence > operator.opType.precedence) ||
                            (top.opType.precedence == operator.opType.precedence && operator.opType.associativity == Associativity.LEFT)

                if (shouldPop) {
                    output += operatorStack.removeLast()
                } else {
                    break
                }
            }
            operatorStack.addLast(operator)
        }

        for (t in tokens) {
            when (t) {
                is CalculatorToken.Number -> output += t
                is CalculatorToken.Op -> pushOperator(t)
            }
        }
        while (operatorStack.isNotEmpty()) output += operatorStack.removeLast()
        return output
    }
}