import java_cup.runtime.*;


%%

%unicode
%cup
%line
%column
%class Lexer

%{
  private Symbol symbol(int type) {
    return new Symbol(type, yyline, yycolumn);
  }
  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline, yycolumn, value);

  }
%}

id = [a-zA-Z_][a-zA-Z0-9_]*
new_line = \r|\n|\r\n;
wh = {new_line} | [\ \t\f]

cadena = \"[^\"\r\n]*\"
real = (([0-9]+\.[0-9]*) | ([0-9]*\.[0-9]+)) (e|E(\+|\-)?[0-9]+)?
entero = 0 | [1-9][0-9]*
// Expresion regular para reconocer un caracter con las siguientes condiciones: Para las constantes se emplea la misma
                                                                                //sintaxis que en Java, usando comillas simples, (por ejemplo ‘a’) y pudiendo usar las secuencias de escape
                                                                                //al igual que en Java para los caracteres especiales (‘\b’,’\n’,’\f’,’\r’,’\t’,’\”’,’\\’,’\’’)
char = \'(\\b|\\n|\\f|\\r|\\t|\\\"|\\\\|\\\'|[^\\\'\r\n])\'
unicode_char = \'(\\u[0-9a-fA-F]{4})\'

%%

"("       {return symbol(sym.AP);}
")"       {return symbol(sym.CP);}
"{"       {return symbol(sym.ALL);}
"}"       {return symbol(sym.CLL);}
"["       {return symbol(sym.AC);}
"]"       {return symbol(sym.CC);}
";"       {return symbol(sym.PYC);}
","       {return symbol(sym.COMA);}
"+"       {return symbol(sym.MAS, "SUMA");}
"-"       {return symbol(sym.MENOS, "RESTA");}
"*"       {return symbol(sym.POR, "MULT");}
"/"       {return symbol(sym.DIV, "DIV");}
"="       {return symbol(sym.ASIGNA);}
"=="      {return symbol(sym.EQ);}
"!="      {return symbol(sym.NEQ);}
"&&"      {return symbol(sym.AND);}
"||"      {return symbol(sym.OR);}
"!"       {return symbol(sym.NOT);}
">"       {return symbol(sym.GT);}
"<"       {return symbol(sym.LT);}
">="      {return symbol(sym.GE);}
"<="      {return symbol(sym.LE);}
"if"      {return symbol(sym.IF, PLXC.tablaSimbolos.getNewEtiq());}
"else"    {return symbol(sym.ELSE);}
"else if" {return symbol(sym.ELSEIF, PLXC.tablaSimbolos.getNewEtiq());}
"print"   {return symbol(sym.PRINT);}
"while"   {return symbol(sym.WHILE, PLXC.tablaSimbolos.getNewEtiq());}
"for"     {return symbol(sym.FOR, PLXC.tablaSimbolos.getNewEtiq());}
"do"      {return symbol(sym.DO, PLXC.tablaSimbolos.getNewEtiq());}

"true"    {return symbol(sym.TRUE);}
"false"   {return symbol(sym.FALSE);}

"int"     { return symbol(sym.INT);}
"float"   { return symbol(sym.FLOAT);}
"char"    { return symbol(sym.CHAR);}
"String"  { return symbol(sym.STRING);}

{unicode_char} {
          return symbol(sym.CARACTER, Integer.parseInt(yytext().substring(3, 7), 16)); }
{id}      { return symbol(sym.ID, yytext()); }
{cadena}  { return symbol(sym.CADENA, new String(yytext().substring(1,yytext().length()-1))); }
{entero}  { return symbol(sym.NUM_ENTERO, Integer.valueOf(yytext())); }
{real}    { return symbol(sym.NUM_REAL, Float.valueOf(yytext())); }
{char}    { return symbol(sym.CARACTER, Integer.valueOf(yytext().charAt(1))); }


// Expresion regular para reconocer un comentario de una línea
\/\/[^\r\n]*(\r|\n|\r\n) {;}

// Expresion regular para reconocer un comentario de varias líneas
\/\*([^\*]|\*[^\/])*\*\/ {;}

{wh}      {;}

[^]     { throw new RuntimeException("Error en el análisis léxico. Cadena no válida" + yytext());}