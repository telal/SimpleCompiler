package ru.telal.compiler.nodes.grammer

import ru.telal.compiler.visitor.Visitor

class ProgramContext(private val statements: List<StatementContext>) : ParserRuleContext() {

    init {
        for (statement in statements) {
            addChild(statement)
        }
    }
    fun getStatements(i: Int): StatementContext? = statements[i]

    override fun <T> accept(visitor: Visitor<T>): T? {
        return visitor.visitProgram(this)
    }
}