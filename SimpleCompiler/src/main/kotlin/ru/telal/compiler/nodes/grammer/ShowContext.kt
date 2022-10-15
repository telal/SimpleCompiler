package ru.telal.compiler.nodes.grammer

import ru.telal.compiler.nodes.TerminalNode
import ru.telal.compiler.visitor.Visitor

class ShowContext(
    val integerValue: TerminalNode?,
    val variableName: TerminalNode?
) : ParserRuleContext() {

    init {
        if (integerValue != null) {
            this.addChild(integerValue)
        } else {
            this.addChild(variableName)
        }
    }

    override fun <T> accept(visitor: Visitor<T>): T? = visitor.visitShow(this)
}