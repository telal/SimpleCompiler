package ru.telal.compiler.nodes.grammer

import ru.telal.compiler.nodes.TerminalNode
import ru.telal.compiler.visitor.Visitor

class LetContext(
    val variableName: TerminalNode,
    val variableValue: TerminalNode
) : ParserRuleContext() {

    init {
        addChild(variableName);
        addChild(variableValue);
    }

    override fun <T> accept(visitor: Visitor<out T>): T {
        return visitor.visitLet(this)
    }
}