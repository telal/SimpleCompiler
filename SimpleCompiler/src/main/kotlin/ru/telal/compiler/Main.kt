import ru.telal.compiler.Lexer
import ru.telal.compiler.Parser
import ru.telal.compiler.utils.getResourceAsText
import ru.telal.compiler.visitor.CodeGeneratorVisitor
import ru.telal.compiler.visitor.InterpreterVisitor
import ru.telal.compiler.visitor.SemanticAnalyzer
import java.io.IOException

private const val FILE_NAME = "DummyLangSample"
fun main(args: Array<String>) {
    val sourceCode = getResourceAsText("/files/$FILE_NAME.dl")
    if (sourceCode != null) {
        val lexer = Lexer(sourceCode)
        val parser = Parser(lexer)
        val tree = parser.parseProgram()
        tree.accept(SemanticAnalyzer())
        tree.accept(InterpreterVisitor())
        tree.accept(CodeGeneratorVisitor(FILE_NAME))
    }

}