lexer grammar MapaLexer;

// Tokens léxicos
STRING: '"' (~["])* '"'; // Coincide con cualquier texto entre comillas dobles
NUMERO: [0-9]+;          // Coincide con cualquier secuencia de dígitos
ESPACIOS: [ \t\r\n]+ -> skip; // Ignora espacios en blanco, tabulaciones y saltos de línea
TEDA:'te da';
PUNTOS:'puntos';
ESTAENTERRADOEN:'esta enterrado en';
COMA:',';
ZONA: 'Zona';
LIMITES: 'limites';