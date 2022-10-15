package ru.telal.compiler.tree

import ru.telal.compiler.visitor.Visitable

interface ParseTree : Visitable {

    var parent: ParseTree?

    fun getText(): String?

    fun getPayload(): Any?

    fun addChild(child: ParseTree?)

    fun getChild(i: Int): ParseTree?

    fun getChildCount(): Int

    fun toStringTree(): String?
}