import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class AnalizadorListener extends MapaParserBaseListener{
    
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
    public void enterMapa(MapaParser.MapaContext ctx) {
        try { writeIndented("Mapa:"); indentLevel++; } catch (IOException e) { e.printStackTrace(); }
    }

    @Override
    public void exitMapa(MapaParser.MapaContext ctx) {
        indentLevel--;
    }

    @Override
    public void enterTitulo(MapaParser.TituloContext ctx) {
        try { writeIndented("Título: " + ctx.getText()); } catch (IOException e) { e.printStackTrace(); }
    }

    @Override
    public void exitTitulo(MapaParser.TituloContext ctx) {}

    @Override
    public void enterPuntos(MapaParser.PuntosContext ctx) {
        try { writeIndented("Puntos: " + ctx.getText()); } catch (IOException e) { e.printStackTrace(); }
    }

    @Override
    public void exitPuntos(MapaParser.PuntosContext ctx) {}

    @Override
    public void enterUbicacion(MapaParser.UbicacionContext ctx) {
        try { writeIndented("Ubicación: " + ctx.getText()); } catch (IOException e) { e.printStackTrace(); }
    }

    @Override
    public void exitUbicacion(MapaParser.UbicacionContext ctx) {}

    @Override
    public void enterCoordenada(MapaParser.CoordenadaContext ctx) {
        try { writeIndented("Coordenada: " + ctx.getText()); } catch (IOException e) { e.printStackTrace(); }
    }

    @Override
    public void exitCoordenada(MapaParser.CoordenadaContext ctx) {}

    @Override
    public void enterZona(MapaParser.ZonaContext ctx) {
        try { writeIndented("Zona: " + ctx.getText()); } catch (IOException e) { e.printStackTrace(); }
    }

    @Override
    public void exitZona(MapaParser.ZonaContext ctx) {}

    public void close() throws IOException {
        writer.close();
    }

}