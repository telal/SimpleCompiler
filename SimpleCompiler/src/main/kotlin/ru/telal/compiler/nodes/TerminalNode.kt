package ru.telal.compiler.nodes

import ru.telal.compiler.token.Token
import ru.telal.compiler.tree.ParseTree
import ru.telal.compiler.visitor.Visitor

class TerminalNode : ParseTree {

    var symbol: Token? = null

    private var parent: ParseTree? = null

    override fun getParent(): ParseTree? = parent

    override fun setParent(parent: ParseTree?) {
        this.parent = parent
    }

    override fun getText(): String? = symbol?.value

    override fun getPayload(): Any? = symbol

    override fun addChild(child: ParseTree) {}

    override fun getChild(i: Int): ParseTree? = null

    override fun getChildCount(): Int = 0

    override fun toStringTree(): String? = getText()

    override fun <T> accept(visitor: Visitor<out T>): T? {
        return visitor.visitTerminal(this)
    }
}