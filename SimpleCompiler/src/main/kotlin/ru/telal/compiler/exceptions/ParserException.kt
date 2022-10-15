package ru.telal.compiler.exceptions

class ParserException : RuntimeException {

    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
}