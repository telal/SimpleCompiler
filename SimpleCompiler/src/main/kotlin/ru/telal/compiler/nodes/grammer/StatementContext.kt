package ru.telal.compiler.nodes.grammer

import ru.telal.compiler.visitor.Visitor

class StatementContext(
    val letContext: LetContext?,
    val showContext: ShowContext?
) : ParserRuleContext() {

    init {
        if (letContext != null) {
            this.addChild(letContext);
        } else {
            this.addChild(showContext);
        }
    }

    override fun <T> accept(visitor: Visitor<out T>): T {
        return visitor.visitStatement(this)
    }
}