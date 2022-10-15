package ru.telal.compiler.nodes.grammer

import ru.telal.compiler.tree.ParseTree
import ru.telal.compiler.visitor.Visitor

open class ParserRuleContext : ParseTree {

    private var parent: ParseTree? = null

    private val children = mutableListOf<ParseTree>()

    override fun getParent(): ParseTree? = parent

    override fun setParent(parent: ParseTree?) {
        this.parent = parent
    }

    override fun getText(): String? {
        if (getChildCount() == 0) return ""
        val builder = StringBuilder()
        for (i in 0 until getChildCount()) {
            builder.append(getChild(i).getText())
        }
        return builder.toString()
    }

    override fun getPayload(): Any? = this

    override fun addChild(child: ParseTree) {
        child.setParent(this)
        children.add(child)
    }

    override fun getChild(i: Int): ParseTree {
        return children[i]
    }

    override fun getChildCount(): Int {
        return if (children.isNotEmpty()) {
            children.size
        } else {
            0
        }
    }

    override fun toStringTree(): String? {
        if (getChildCount() == 0) return ""
        val sb = StringBuilder()
        sb.append("( ")
        sb.append(this.javaClass.simpleName)
        sb.append("(")
        for (i in 0 until getChildCount()) {
            sb.append(" ").append(getChild(i).toStringTree()).append(" ")
        }
        sb.append(")")
        sb.append(" )")
        return sb.toString()
    }

    override fun <T> accept(visitor: Visitor<out T>): T? = null
}