package ru.telal.compiler

import ru.telal.compiler.exceptions.ParserException
import ru.telal.compiler.nodes.TerminalNode
import ru.telal.compiler.nodes.grammer.LetContext
import ru.telal.compiler.nodes.grammer.ProgramContext
import ru.telal.compiler.nodes.grammer.ShowContext
import ru.telal.compiler.nodes.grammer.StatementContext
import ru.telal.compiler.token.Token
import ru.telal.compiler.token.TokenType

class Parser(private val lexer: Lexer) {

    fun parseProgram(): ProgramContext {
        val statements = mutableListOf<StatementContext>()
        do {
            statements.add(parseStatement())
        } while (lexer.hasNextToken())
        return ProgramContext(statements)
    }
    fun parseStatement(): StatementContext {
        if (lexer.currentToken == null) {
            lexer.hasNextToken()
        }
        val token = lexer.currentToken
        return when (token?.type) {
            TokenType.VARIABLE -> {
                StatementContext(parseLet(), null)
            }
            TokenType.SHOW -> {
                StatementContext(null, parseShow())
            }
            else -> {
                throw ParserException("Not of type LET or SHOW.")
            }
        }
    }

    fun parseLet(): LetContext? {
        if (lexer.currentToken == null) {
            lexer.hasNextToken()
        }
        val variableNameToken = parseTerminalNode()
        lexer.hasNextToken()
        lexer.hasNextToken()
        val valueToken = parseTerminalNode()
        return LetContext(variableNameToken, valueToken)
    }

    fun parseShow(): ShowContext? {
        if (lexer.currentToken == null) {
            lexer.hasNextToken()
        }
        lexer.hasNextToken()
        val terminal = parseTerminalNode()
        val token = terminal.getPayload() as Token
        return when (token.type) {
            TokenType.NUMBER -> {
                ShowContext(terminal, null)
            }
            TokenType.VARIABLE -> {
                ShowContext(null, terminal)
            }
            else -> {
                throw ParserException("Show not preceded with var or int")
            }
        }
    }

    fun parseTerminalNode(): TerminalNode {
        if (lexer.currentToken == null) {
            lexer.hasNextToken()
        }
        val token = TerminalNode()
        token.symbol = lexer.currentToken
        return token
    }
}