package ru.telal.compiler.visitor

import ru.telal.compiler.exceptions.SemanticException
import ru.telal.compiler.nodes.grammer.LetContext
import ru.telal.compiler.nodes.grammer.ShowContext
import ru.telal.compiler.nodes.grammer.StatementContext

class SemanticAnalyzer : SimplerLangBaseVisitor<Void>() {

    private val variableMap = mutableMapOf<String, String>()

    override fun visitStatement(context: StatementContext): Void? {
        if (context.letContext == null && context.showContext == null) {
            throw SemanticException("Statement should of type LET or SHOW.")
        } else if (context.letContext != null && context.showContext != null) {
            throw SemanticException("Statement should be either of type LET or SHOW & not both.")
        }
        return super.visitStatement(context)
    }

    override fun visitLet(context: LetContext): Void? {
        val variableName = context.variableName.getText()
        val variableValue = context.variableValue.getText()

        if (variableName.isNullOrEmpty() || variableName.isEmpty()) {
            throw SemanticException("Variable name cannot be empty.")
        } else if (variableValue.isNullOrEmpty()) {
            throw SemanticException("Variable value cannot be empty.")
        }

        try {
            variableValue.toInt()
        } catch (ex: NumberFormatException) {
            throw SemanticException("Variable value should be integer.", ex)
        } catch (ex: NullPointerException) {
            throw SemanticException("Variable value should be integer.", ex)
        }

        variableMap[variableName] = variableValue

        return super.visitLet(context)
    }

    override fun visitShow(context: ShowContext): Void? {
        val variableNameTN = context.variableName
        val integerValueTN = context.integerValue

        val isVarPresent = variableNameTN != null && variableNameTN.getText()?.isNotEmpty() == true
        val isIntPresent = integerValueTN != null && integerValueTN.getText()?.isNotEmpty() == true

        if (!isVarPresent && !isIntPresent) {
            throw SemanticException("SHOW should have integer or variable as argument")
        } else if (isVarPresent && isIntPresent) {
            throw SemanticException("SHOW should have either integer or variable as argument")
        }

        try {
            integerValueTN?.getText()?.toInt()
        } catch (ex: NumberFormatException) {
            throw SemanticException("SHOW argument is not a valid integer.", ex)
        } catch (ex: NullPointerException) {
            throw SemanticException("SHOW argument is not a valid integer.", ex)
        }

        if (variableNameTN != null && !variableMap.containsKey(variableNameTN.getText())) {
            throw SemanticException("SHOW argument variable is not declared.")
        }
        return super.visitShow(context)
    }
}