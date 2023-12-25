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
real = (([0-9]+\.[0-9]*) | ([0-9]*\.[0-9]+)) (e|E('+'|'-')?[0-9]+)?
entero = 0 | [1-9][0-9]*
// Expresion regular para reconocer un caracter con las siguientes condiciones: Para las constantes se emplea la misma
                                                                                //sintaxis que en Java, usando comillas simples, (por ejemplo ‘a’) y pudiendo usar las secuencias de escape
                                                                                //al igual que en Java para los caracteres especiales (‘\b’,’\n’,’\f’,’\r’,’\t’,’\”’,’\\’,’\’’)
char = \'(\\b|\\n|\\f|\\r|\\t|\\\"|\\\\|\\\'|[^\\\'\r\n])\'

%%

"("       {return symbol(sym.AP);}
")"       {return symbol(sym.CP);}
"{"       {return symbol(sym.ALL);}
"}"       {return symbol(sym.CLL);}
"["       {return symbol(sym.AC);}
"]"       {return symbol(sym.CC);}
";"       {return symbol(sym.PYC);}
"="       {return symbol(sym.ASIGNA);}
"=="      {return symbol(sym.EQ);}
"!="      {return symbol(sym.NEQ);}
"&"       {return symbol(sym.AND);}
"|"       {return symbol(sym.OR);}
"!"       {return symbol(sym.NOT);}
">"       {return symbol(sym.GT);}
"<"       {return symbol(sym.LT);}
">="      {return symbol(sym.GE);}
"<="      {return symbol(sym.LE);}
"if"      {return symbol(sym.IF);}
"else"    {return symbol(sym.ELSE);}
"while"   {return symbol(sym.WHILE);}
"for"     {return symbol(sym.FOR);}
"do"      {return symbol(sym.DO);}

"int"     { return symbol(sym.INT);}
"float"   { return symbol(sym.FLOAT);}
"char"    { return symbol(sym.CHAR);}
"string"  { return symbol(sym.STRING);}

{id}      { return symbol(sym.ID, yytext()); }
{cadena}  { return symbol(sym.STRING, new String(yytext().substring(1,yytext().length()-1))); }
{real}    { return symbol(sym.NUM_REAL, Float.valueOf(yytext())); }
{entero}  { return symbol(sym.NUM_ENTERO, Integer.valueOf(yytext())); }
{char}    { return symbol(sym.CHAR, new Character(yytext().charAt(1))); }

{wh}      {;}

[^]     { throw new RuntimeException("Error en el análisis léxico. Cadena no válida" + yytext());}