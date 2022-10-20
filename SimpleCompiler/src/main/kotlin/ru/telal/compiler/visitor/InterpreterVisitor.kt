package ru.telal.compiler.visitor

import ru.telal.compiler.nodes.grammer.LetContext
import ru.telal.compiler.nodes.grammer.ShowContext


class InterpreterVisitor : SimplerLangBaseVisitor<Void>(){

    private var variableMap = mutableMapOf<String, String>()

    override fun visitLet(context: LetContext): Void? {
        variableMap[context.variableName.getText()] = context.variableValue.getText()
        return super.visitLet(context)
    }

    override fun visitShow(context: ShowContext): Void? {
        if (context.integerValue != null) {
            println(context.integerValue.getText())
        } else if (context.variableName != null) {
            println(variableMap[context.variableName.getText()])
        }
        return super.visitShow(context)
    }
}