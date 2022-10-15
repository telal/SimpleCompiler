package ru.telal.compiler.visitor

interface Visitable {

    fun <T> accept(visitor: Visitor<T>): T?
}