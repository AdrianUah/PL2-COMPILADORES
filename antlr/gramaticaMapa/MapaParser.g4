parser grammar MapaParser;

options{
    tokenVocab = MapaLexer;
    language = Java;
}

// Regla de inicio (parse rule) que define la estructura principal
mapa: (titulo | puntos | ubicacion | zona)* EOF;

// Regla para el título del mapa
titulo: STRING;

// Regla para los puntos asignados a cada barco
puntos: STRING TEDA NUMERO PUNTOS;

// Regla para las ubicaciones de los barcos con múltiples coordenadas
ubicacion: STRING ESTAENTERRADOEN coordenada (COMA coordenada)*;

// Regla para coordenadas (pares de números separados por coma)
coordenada: NUMERO COMA NUMERO;

// Regla para zonas
zona: ZONA STRING LIMITES coordenada COMA coordenada COMA coordenada;
