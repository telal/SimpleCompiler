package ru.telal.compiler.nodes

import ru.telal.compiler.token.Token
import ru.telal.compiler.tree.ParseTree
import ru.telal.compiler.visitor.Visitor

class TerminalNode : ParseTree {

    var symbol: Token? = null

    override var parent: ParseTree? = null

    override fun getText(): String = symbol?.value ?: ""

    override fun getPayload(): Any? = symbol

    override fun addChild(child: ParseTree?) {}

    override fun getChild(i: Int): ParseTree? = null

    override fun getChildCount(): Int = 0

    override fun toStringTree(): String? = getText()

    override fun <T> accept(visitor: Visitor<T>): T? = visitor.visitTerminal(this)
}