import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.FileInputStream;
import java.io.InputStream;


public class Analizador{



    public static void main(String[] args) throws Exception{

        String outputFile = "Arbol.html"; // Archivo de salida para el AST
        String inputFile=null;
        if(args.length>0) inputFile=args[0];


        InputStream is =System.in;
        if(inputFile!=null) is =new FileInputStream(inputFile);
    
    
        ANTLRInputStream input =new ANTLRInputStream(is);


        MapaLexer lexer = new MapaLexer(input);


        CommonTokenStream tokens =new CommonTokenStream(lexer);


        MapaParser parser = new MapaParser(tokens);


        ParseTree tree = parser.mapa();

        ParseTreeWalker walker = new ParseTreeWalker();
        AnalizadorListener escuchador = new AnalizadorListener(outputFile);
        walker.walk(escuchador,tree);

        escuchador.close();
        System.out.println(tree.toStringTree(parser));
    }
}