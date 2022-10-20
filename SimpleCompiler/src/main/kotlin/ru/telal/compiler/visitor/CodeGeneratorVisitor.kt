package ru.telal.compiler.visitor

import org.objectweb.asm.ClassWriter
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.Type
import ru.telal.compiler.nodes.grammer.LetContext
import ru.telal.compiler.nodes.grammer.ProgramContext
import ru.telal.compiler.nodes.grammer.ShowContext
import ru.telal.compiler.utils.writeByteArrayToFile
import java.io.IOException

class CodeGeneratorVisitor(private val fileName: String) : SimplerLangBaseVisitor<Void>() {

    private val classWriter = ClassWriter(0);
    private var mainMethodVisitor: MethodVisitor? = null

    private val variableIndexMap = mutableMapOf<String, Int>()
    private var variableIndex = 0

    override fun visitProgram(context: ProgramContext): Void? {
        classWriter.visit(
            V1_8,
            ACC_PUBLIC + ACC_SUPER,
            fileName,
            null,
            "java/lang/Object",
            null
        )
        mainMethodVisitor =
            classWriter.visitMethod(
                ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null
            )
        super.visitProgram(context)

        mainMethodVisitor?.visitEnd()

        classWriter.visitEnd()

        try {
            writeByteArrayToFile(classWriter.toByteArray(), "output/${fileName}.class")
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return null
    }

    override fun visitLet(context: LetContext): Void? {
        val variableIntegerVal = context.variableValue.getText().toInt()
        mainMethodVisitor?.let {
            it.visitIntInsn(BIPUSH, variableIntegerVal)
            it.visitMethodInsn(
                INVOKESTATIC,
                Type.getType(Int::class.java).internalName,
                "valueOf",
                "(I)Ljava/lang/Integer;",
                false
            )
            it.visitVarInsn(ASTORE, variableIndex)
        }
        variableIndexMap[context.variableName.getText()] = variableIndex
        variableIndex++
        return null
    }

    override fun visitShow(context: ShowContext): Void? {
        mainMethodVisitor?.let {
            it.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;")
            if (context.variableName != null) {
                val index = variableIndexMap[context.variableName.getText()]
                if (index != null) {
                    it.visitVarInsn(ALOAD, index)
                    it.visitMethodInsn(
                        INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/Object;)V", false
                    )
                }
            } else if (context.integerValue != null) {
                val integerVal = context.integerValue.getText().toInt()
                it.visitIntInsn(BIPUSH, integerVal)
                it.visitMethodInsn(
                    INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false
                )
            }
        }
        return null
    }
}