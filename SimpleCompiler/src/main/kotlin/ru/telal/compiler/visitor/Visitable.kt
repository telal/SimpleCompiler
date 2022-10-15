package ru.telal.compiler.visitor

interface Visitable {

    fun <T> accept(visitor: Visitor<out T>): T?
}