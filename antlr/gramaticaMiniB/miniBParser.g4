parser grammar miniBParser;

options{
    tokenVocab = miniBLexer;
    language = Java;
}

// Regla de inicio para el análisis del programa
programa: (sentencia | comentario)* EOF;

// Definición de las sentencias posibles
sentencia: asignacion | funcion | expresion | condicion | ciclo ;

//Regla para la declaración de comentarios
comentario: COMENTARIOLINEA textos*; //FINAL_COMENTARIO_UL;
textos: TEXTOCOMENTARIOUL+;

// Regla para la asignación de valores a variables
asignacion: LET? identificador ASIGNACION expresion;

// Expresiones aritméticas básicas
expresion: expresion (MAS | MENOS | POR | DIVIDIR | MAYOR | MENOR | MOD | ASIGNACION) expresion
         | LPAREN expresion RPAREN
         | identificador
         | numero
         | funcion
         | string;
         

// Regla para las estructuras de control
condicion: IF expresion THEN sentencia* (ELSE sentencia*)? END
        | SWITCH expresion (CASE expresion PUNTOPUNTO sentencia* BREAK)* (DEFAULT PUNTOPUNTO sentencia*)? END;


// Regla para los ciclos (while, for y repeat)
ciclo: WHILE expresion sentencia* END
     | FOR  expresion TO expresion sentencia+ NEXT
     | REPEAT sentencia+ UNTIL expresion;
     
// Funciones de MiniB
funcion: VAL  expresion 
       | LEN  expresion 
       | ISNAN  expresion
       | PRINT expresion
       | EXIT
       | CONTINUE
       | INPUT string identificador
       | funcion_usuario;

// Funcion del usuario       
funcion_usuario: identificador LPAREN (identificador (COMA identificador)*)? RPAREN bloque END;

bloque: (sentencia | comentario)*;


//identificador, numero, string
identificador:IDENTIFICADOR;

numero:NUMERO;

string:STRING;