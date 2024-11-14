import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class AnalizadorListener implements miniBParserListener {

    private BufferedWriter writer;
    private int indentLevel = 0; // Control de niveles de indentación

    public AnalizadorListener(String filename) throws IOException {
        writer = new BufferedWriter(new FileWriter(filename));
    }

    private void writeIndented(String text) throws IOException {
        for (int i = 0; i < indentLevel; i++) writer.write("  ");
        writer.write(text);
        writer.newLine();
    }

    @Override
    public void enterPrograma(miniBParser.ProgramaContext ctx) {
        try { writeIndented("Programa:"); indentLevel++; } catch (IOException e) { e.printStackTrace(); }
    }

    @Override
    public void exitPrograma(miniBParser.ProgramaContext ctx) {
        indentLevel--;
    }

    @Override
    public void enterSentencia(miniBParser.SentenciaContext ctx) {
        try { writeIndented("Sentencia: " + ctx.getText()); } catch (IOException e) { e.printStackTrace(); }
    }

    @Override
    public void exitSentencia(miniBParser.SentenciaContext ctx) {}

    @Override
    public void enterComentario(miniBParser.ComentarioContext ctx) {
        try { writeIndented("Comentario: " + ctx.getText()); } catch (IOException e) { e.printStackTrace(); }
    }

    @Override
    public void exitComentario(miniBParser.ComentarioContext ctx) {}

    @Override
    public void enterTextos(miniBParser.TextosContext ctx) {
        try { writeIndented("Textos: " + ctx.getText()); } catch (IOException e) { e.printStackTrace(); }
    }

    @Override
    public void exitTextos(miniBParser.TextosContext ctx) {}

    @Override
    public void enterAsignacion(miniBParser.AsignacionContext ctx) {
        try { writeIndented("Asignación: " + ctx.getText()); } catch (IOException e) { e.printStackTrace(); }
    }

    @Override
    public void exitAsignacion(miniBParser.AsignacionContext ctx) {}
/* 
    @Override
    public void enterDeclaracion(miniBParser.DeclaracionContext ctx) {
        try { writeIndented("Declaración: " + ctx.getText()); } catch (IOException e) { e.printStackTrace(); }
    }

    @Override
    public void exitDeclaracion(miniBParser.DeclaracionContext ctx) {}
*/
    @Override
    public void enterExpresion(miniBParser.ExpresionContext ctx) {
        try { writeIndented("Expresión: " + ctx.getText()); } catch (IOException e) { e.printStackTrace(); }
    }

    @Override
    public void exitExpresion(miniBParser.ExpresionContext ctx) {}

    @Override
    public void enterCondicion(miniBParser.CondicionContext ctx) {
        try { writeIndented("Condición: " + ctx.getText()); } catch (IOException e) { e.printStackTrace(); }
    }

    @Override
    public void exitCondicion(miniBParser.CondicionContext ctx) {}

    @Override
    public void enterCiclo(miniBParser.CicloContext ctx) {
        try { writeIndented("Ciclo: " + ctx.getText()); } catch (IOException e) { e.printStackTrace(); }
    }

    @Override
    public void exitCiclo(miniBParser.CicloContext ctx) {}

    @Override
    public void enterFuncion(miniBParser.FuncionContext ctx) {
        try { writeIndented("Función: " + ctx.getText()); } catch (IOException e) { e.printStackTrace(); }
    }
    @Override
    public void enterString(miniBParser.StringContext ctx) {
        try {
            writeIndented("String: " + ctx.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exitString(miniBParser.StringContext ctx) {
    }

    @Override
    public void enterIdentificador(miniBParser.IdentificadorContext ctx) {
        try {
            writeIndented("Identificador: " + ctx.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exitIdentificador(miniBParser.IdentificadorContext ctx) {
    }

    @Override
    public void enterNumero(miniBParser.NumeroContext ctx) {
        try {
            writeIndented("Número: " + ctx.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void enterFuncion_usuario(miniBParser.Funcion_usuarioContext ctx) {
        try {
            writeIndented("Entrando a función de usuario: " + ctx.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exitFuncion_usuario(miniBParser.Funcion_usuarioContext ctx) {
        try {
            writeIndented("Saliendo de función de usuario: " + ctx.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void enterBloque(miniBParser.BloqueContext ctx) {
        try {
            writeIndented("Entrando a bloque: " + ctx.getText());
            indentLevel++; // Aumentar el nivel de indentación para el contenido del bloque
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exitBloque(miniBParser.BloqueContext ctx) {
        try {
            indentLevel--; // Reducir el nivel de indentación al salir del bloque
            writeIndented("Saliendo de bloque: " + ctx.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exitNumero(miniBParser.NumeroContext ctx) {
    }


    @Override
    public void exitFuncion(miniBParser.FuncionContext ctx) {}

    @Override
    public void enterEveryRule(ParserRuleContext ctx) {}

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {}

    @Override
    public void visitTerminal(TerminalNode node) {}

    @Override
    public void visitErrorNode(ErrorNode node) {}

    public void close() throws IOException {
        writer.close();
    }
}
