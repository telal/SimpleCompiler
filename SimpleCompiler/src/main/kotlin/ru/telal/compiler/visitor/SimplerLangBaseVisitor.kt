package ru.telal.compiler.visitor

import ru.telal.compiler.nodes.TerminalNode
import ru.telal.compiler.nodes.grammer.LetContext
import ru.telal.compiler.nodes.grammer.ProgramContext
import ru.telal.compiler.nodes.grammer.ShowContext
import ru.telal.compiler.nodes.grammer.StatementContext
import ru.telal.compiler.tree.ParseTree

open class SimplerLangBaseVisitor<T> : Visitor<T> {

    override fun visitProgram(context: ProgramContext) = visitChildren(context)

    override fun visitStatement(context: StatementContext): T? = visitChildren(context)

    override fun visitLet(context: LetContext): T? = visitChildren(context)

    override fun visitShow(context: ShowContext): T? = visitChildren(context)

    override fun visitTerminal(context: TerminalNode): T?  = defaultResult()

    private fun visitChildren(node: ParseTree): T? {
        var result = defaultResult()
        for (i in 0 until node.getChildCount()) {
            val c = node.getChild(i)
            result = c?.accept(this)
        }
        return result
    }

    protected fun defaultResult(): T? = null
}