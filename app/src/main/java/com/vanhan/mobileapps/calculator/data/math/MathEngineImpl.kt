package com.vanhan.mobileapps.calculator.data.math

import com.vanhan.mobileapps.calculator.domain.math.MathEngine

class MathEngineImpl : MathEngine {

    override fun evaluate(expressionUiSymbols: String): String {
        val normalizedExpression = normalize(expressionUiSymbols)
        val tokens = Tokenizer.tokenize(normalizedExpression)
        val rpn = ShuntingYard.toRpn(tokens)
        val result = RpnEvaluator.evaluate(rpn)
        return result.stripTrailingZeros().toPlainString()
    }

    override fun applyPercentToLastOperand(expressionUiSymbols: String): String {
        TODO("Not yet implemented")
    }

    private fun normalize(ui: String): String =
        ui.replace('−', '-').replace('×', '*').replace('÷', '/')
}