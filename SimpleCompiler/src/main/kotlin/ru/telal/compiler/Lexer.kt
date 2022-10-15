package ru.telal.compiler

import ru.telal.compiler.exceptions.LexerException
import ru.telal.compiler.token.Token
import ru.telal.compiler.token.TokenType
import java.util.*

class Lexer(val code: String) {

    private val codeLength = code.length
    private var currentIndex = 0
    private val symbols = setOf(' ', '\r', '\t', '\n')
    var currentToken: Token? = null
        private set
    var previousToken: Token? = null
        private set

    fun hasNextToken(): Boolean {
        while (!isEndOfCode()) {
            previousToken = currentToken
            val currentChar = code[currentIndex]
            if (symbols.contains(currentChar)) {
                skipWhiteSpace()
                continue
            } else if (currentChar == '=') {
                currentToken = Token(TokenType.EQUALS_OPERATOR)
                currentIndex++
            } else if (Character.isDigit(currentChar)) {
                currentToken = Token(TokenType.NUMBER, readNumber())
            } else if (Character.isLetter(currentChar)) {
                val variableName = readVariable()
                currentToken = if (variableName.equals("show", ignoreCase = true)) {
                    Token(TokenType.SHOW)
                } else {
                    Token(TokenType.VARIABLE, variableName)
                }
            } else {
                throw LexerException("Token $currentChar is not defined")
            }
            return true
        }
        return false
    }

    private fun readNumber(): String {
        val result = StringBuilder()
        var currentChar = code[currentIndex]
        while (!isEndOfCode() && Character.isDigit(currentChar)) {
            result.append(currentChar)
            currentIndex++
            if (isEndOfCode()) break
            currentChar = code[currentIndex]
        }
        return result.toString()
    }

    private fun readVariable(): String {
        val sb = StringBuilder()
        var currentChar = code[currentIndex]
        while (!isEndOfCode() && Character.isLetter(currentChar)) {
            sb.append(currentChar)
            currentIndex++
            if (isEndOfCode()) break
            currentChar = code[currentIndex]
        }
        return sb.toString()
    }

    private fun skipWhiteSpace() {
        while (!isEndOfCode()) {
            if (!symbols.contains(code[currentIndex])) break
            currentIndex++
        }
    }

    private fun isEndOfCode(): Boolean = currentIndex >= codeLength
}