import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

// Clase Tesoro que contiene la ubicación y el puntaje del tesoro
class Tesoro {
    int x;
    int y;
    int puntos;

    Tesoro(int x, int y, int puntos) {
        this.x = x;
        this.y = y;
        this.puntos = puntos;
    }
}

// Clase Mapa que contiene la matriz del mapa y la lista de tesoros
class Mapa {
    private int[][] grid;
    private List<Tesoro> tesoros;
    public int filas;
    public int columnas;
    Mapa(int filas, int columnas) {
        this.filas=filas;
        this.columnas=columnas;

        grid = new int[filas+1][columnas+1];
        tesoros = new ArrayList<>();
    }

    void agregarTesoro(int x, int y, int puntos) {
        tesoros.add(new Tesoro(x, y, puntos));
        grid[x][y] = puntos; // Coloca el tesoro en la matriz
    }

    Tesoro buscarTesoro(int x, int y) {
        Iterator<Tesoro> iterator = tesoros.iterator();
        while (iterator.hasNext()) {
            Tesoro tesoro = iterator.next();
            if (tesoro.x == x && tesoro.y == y) {
                iterator.remove(); // Elimina el tesoro encontrado
                grid[x][y] = 0;
                return tesoro;
            }
        }
        return null; // No hay tesoro en la posición
    }

    boolean quedanTesoros() {
        return !tesoros.isEmpty();
    }
}

// Clase Jugador que mantiene la puntuación actual
class Jugador {
    private int puntuacion;

    Jugador() {
        this.puntuacion = 0;
    }

    void sumarPuntos(int puntos) {
        puntuacion += puntos;
        System.out.println("Puntuación actual: " + puntuacion);
    }
}

// Clase principal Analizador para iniciar el juego y cargar el mapa
public class Juego {
    private Mapa mapa;
    private Jugador jugador;
    private int intentosMaximos;
    private int intentos;

    public Juego(Mapa mapa, int intentosMaximos) {
        this.mapa = mapa;
        this.jugador = new Jugador();
        this.intentosMaximos = intentosMaximos;
        this.intentos = 0;
    }

    // Método para iniciar el juego
    public void iniciarJuego() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("¡Comienza la búsqueda del tesoro!");

        while (mapa.quedanTesoros() && intentos < intentosMaximos) {
            System.out.print("Seleccione una coordenada para bucear (ejemplo 1,3): ");
            String input = scanner.nextLine();
            String[] partes = input.split(",");
        
            // Verifica que el usuario haya introducido dos partes y que sean números válidos
            if (partes.length != 2) {
                System.out.println("Entrada inválida. Por favor, introduzca coordenadas en el formato correcto (ejemplo 1,3).");
                continue; // Salta a la siguiente iteración del bucle
            }
        
            try {
                int x = Integer.parseInt(partes[0]);
                int y = Integer.parseInt(partes[1]);
        
                // Verifica que las coordenadas no sean 0 y mayores de 5
                if (x <= 0 || y <= 0 || x > mapa.filas || y > mapa.columnas) {
                    System.out.println("introduzca valores válidos.");
                    continue; // Salta a la siguiente iteración del bucle
                }
        
                // Busca el tesoro en las coordenadas introducidas
                Tesoro tesoro = mapa.buscarTesoro(x, y); 
                intentos++;
        
                if (tesoro != null) {
                    System.out.println("¡Encontraste un tesoro con " + tesoro.puntos + " puntos!");
                    jugador.sumarPuntos(tesoro.puntos);
                } else {
                    System.out.println("No hay tesoro en esta coordenada. Sigue buscando.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, introduzca números válidos para las coordenadas.");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Coordenadas fuera de rango. Asegúrese de que estén dentro de los límites del mapa.");
            }
        }

        if (!mapa.quedanTesoros()) {
            System.out.println("¡Felicidades, encontraste todos los tesoros!");
        } else if (intentos >= intentosMaximos) {
            System.out.println("Se acabaron los intentos. Fin del juego.");
        }

        scanner.close();
    }

    // Método para cargar el mapa desde un archivo
    public static Mapa cargarMapa(String nombreArchivo) throws Exception {
        InputStream is = new FileInputStream(nombreArchivo);
        MapaLexer lexer = new MapaLexer(CharStreams.fromStream(is));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MapaParser parser = new MapaParser(tokens);
        ParseTree tree = parser.mapa();
        ParseTreeWalker walker = new ParseTreeWalker();
        MapaLoader loader = new MapaLoader();
        walker.walk(loader, tree);
        walker.walk(loader, tree);
        return loader.getMapa();
    }

    public static void main(String[] args) throws Exception {
        String nombreArchivo = args[0];    
        Mapa mapa = cargarMapa(nombreArchivo);
        Juego juego = new Juego(mapa, 5); // máximo 5 intentos
        juego.iniciarJuego();
    }
}

class MapaLoader extends MapaParserBaseListener {
    private Mapa mapa;
    private Map<String, Integer> puntosBarcos = new HashMap<>(); // Mapa para almacenar puntos por barco
    private int maxX = 0; // Coordenada máxima en el eje X
    private int maxY = 0; // Coordenada máxima en el eje Y

    public Mapa getMapa() {
        return mapa;
    }

    @Override
    public void exitPuntos(MapaParser.PuntosContext ctx) {
        if(mapa!=null) {
            String nombreBarco = ctx.STRING().getText().replace("\"", ""); // Elimina comillas
            int puntos = Integer.parseInt(ctx.NUMERO().getText());
            puntosBarcos.put(nombreBarco, puntos); // Almacena el nombre del barco y sus puntos
        }
        
    }

    @Override
    public void exitUbicacion(MapaParser.UbicacionContext ctx) {
        if(mapa!=null) {
            String nombreBarco = ctx.STRING().getText().replace("\"", ""); // Elimina comillas
            for (MapaParser.CoordenadaContext coord : ctx.coordenada()) {
                int x = Integer.parseInt(coord.NUMERO(0).getText());
                int y = Integer.parseInt(coord.NUMERO(1).getText());

                // Busca los puntos del barco utilizando el nombre
                Integer puntos = puntosBarcos.get(nombreBarco);
                if (puntos != null) {
                    mapa.agregarTesoro(x, y, puntos); // Usa los puntos asociados con el barco
                }
            }
        }
    }

    @Override
    public void exitZona(MapaParser.ZonaContext ctx) {
        if(mapa==null) {
            for (MapaParser.CoordenadaContext coord : ctx.coordenada()) {
                int x = Integer.parseInt(coord.NUMERO(0).getText());
                int y = Integer.parseInt(coord.NUMERO(1).getText());

                // Actualiza las coordenadas máximas si es necesario
                if (x > maxX) maxX = x;
                if (y > maxY) maxY = y;
            }
        }
    }

    @Override
    public void exitMapa(MapaParser.MapaContext ctx) {
        if(mapa==null) {
            // Inicializa el mapa con las dimensiones máximas encontradas
            // Si maxX y maxY son 0, es que no se encontraron coordenadas, se puede poner un valor por defecto
            if (maxX == 0) maxX = 5; // Si no hay coordenadas válidas, usaremos un valor por defecto
            if (maxY == 0) maxY = 5; // Si no hay coordenadas válidas, usaremos un valor por defecto
            mapa = new Mapa(maxX, maxY); 
        }
    }
}
