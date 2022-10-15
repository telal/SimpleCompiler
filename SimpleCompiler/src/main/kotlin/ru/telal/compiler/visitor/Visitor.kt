package ru.telal.compiler.visitor

import ru.telal.compiler.nodes.TerminalNode
import ru.telal.compiler.nodes.grammer.LetContext
import ru.telal.compiler.nodes.grammer.ProgramContext
import ru.telal.compiler.nodes.grammer.ShowContext
import ru.telal.compiler.nodes.grammer.StatementContext

interface Visitor<T> {

    fun visitProgram(context: ProgramContext?): T

    fun visitStatement(context: StatementContext?): T

    fun visitLet(context: LetContext?): T

    fun visitShow(context: ShowContext?): T

    fun visitTerminal(context: TerminalNode?): T
}