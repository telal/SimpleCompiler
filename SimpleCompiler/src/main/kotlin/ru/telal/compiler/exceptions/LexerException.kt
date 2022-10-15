package ru.telal.compiler.exceptions

class LexerException : RuntimeException {

    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
}