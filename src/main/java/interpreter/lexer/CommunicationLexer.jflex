package interpreter.lexer;

import java_cup.runtime.*;
import java.util.LinkedList;
import interpreter.abstract_syntax_tree.environment.GlobalError;
%%// Area Break

%public
%class CommunicationLexer
%cup
%cupdebug
%line
%column

/* RegEx */
Identifier = "\"" [^] "\""
Integer = 0 | [1-9][0-9]*
Double = ({Integer})+(".")({Integer}{1,6})
LineTerminator = \r | \n | \r\n
WhiteSpace =  {LineTerminator} | [ \t\f]
InputCharacter = [^\r\n]

BasicCommentary = "//" {InputCharacter}* {LineTerminator}?

%{
    LinkedList<GlobalError> errors;

    public CommunicationLexer(java.io.Reader in, LinkedList<GlobalError> errors) {
        this.zzReader = in;
        this.errors = errors;
    }

    private Symbol symbol(int type) {
        return new Symbol(type, yyline+1, yycolumn+1);
    }

    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline+1, yycolumn+1, value);
    }

    private void print_token(String token) {
        System.out.println(token);
    }

    private void add_error(String value, int line, int column, String description) {
        GlobalError error = new GlobalError(line, column, "lexico", value, description);
        errors.add(error);
    }

%}

%%// Area Break

<YYINITIAL> {
    /* PALABRAS RESERVADAS */
    "solicitud" | "Solicitud"   { print_token(yytext()); return symbol(sym.SOLICITUD, yytext()); }
    "tipo" | "Tipo"             { print_token(yytext()); return symbol(sym.TIPO, yytext()); }
    "nombre" | "Nombre"         { print_token(yytext()); return symbol(sym.NOMBRE, yytext()); }
    "lista" | "Lista"           { print_token(yytext()); return symbol(sym.LISTA, yytext()); }
    "listas" | "Listas"         { print_token(yytext()); return symbol(sym.LISTAS, yytext()); }
    "pistas" | "Pistas"         { print_token(yytext()); return symbol(sym.PISTAS, yytext()); }
    "pista" | "Pista"           { print_token(yytext()); return symbol(sym.PISTA, yytext()); }
    "aleatoria" | "Aleatoria"   { print_token(yytext()); return symbol(sym.ALEATORIA, yytext()); }
    "duracion" | "Duracion"     { print_token(yytext()); return symbol(sym.DURACION, yytext()); }
    "canal" | "Canal"           { print_token(yytext()); return symbol(sym.CANAL, yytext()); }
    "numero" | "Numero"         { print_token(yytext()); return symbol(sym.NUMERO, yytext()); }
    "nota" | "Nota"             { print_token(yytext()); return symbol(sym.NOTA, yytext()); }
    "frecuencia" | "Frecuencia" { print_token(yytext()); return symbol(sym.FRECUENCIA, yytext()); }
    "datos" | "Datos"           { print_token(yytext()); return symbol(sym.DATOS, yytext()); }
    "nota" | "Nota"             { print_token(yytext()); return symbol(sym.NOTA, yytext()); }
    "octava" | "Octava"         { print_token(yytext()); return symbol(sym.OCTAVA, yytext()); }
    "pistanueva" | "Pistanueva" { print_token(yytext()); return symbol(sym.PISTANUEVA, yytext()); }
    "SI" | "si"                 { print_token(yytext()); return symbol(sym.SI, yytext()); }
    "NO" | "no"                 { print_token(yytext()); return symbol(sym.NO, yytext()); }
    "Do" | "Do#" | "Re" | "Re#" | "Mi" | "Fa" | "Fa#" | "Sol" | "Sol#" | "La" | "La#" | "Si" { print_token(yytext()); return symbol(sym.NOTA, yytext()); }
    /* SIMBOLOS */
    ">"                         { print_token(yytext()); return symbol(sym.MAYOR, ">"); }
    "<"                         { print_token(yytext()); return symbol(sym.MENOR, "<"); }
    "/"                         { print_token(yytext()); return symbol(sym.DIAGONAL, "/"); }
    "="                         { print_token(yytext()); return symbol(sym.IGUAL, "="); }
    /* EXPRESIONES REGULARES */
    {Identifier}                { print_token(yytext()); return symbol(sym.ID, yytext()); }
    {Integer}                   { print_token(yytext()); return symbol(sym.INT, Integer.parseInt(yytext())); }
    {Double}                    { print_token(yytext()); return symbol(sym.DOUBLE, Double.parseDouble(yytext())); }
    {WhiteSpace}                { /*Do nothing*/}
    {BasicCommentary}           { /*Do nothing*/}
    [^]                         { add_error(yytext(), yyline+1, yycolumn+1, "No se esperaba el simbolo " + yytext());  }
}
    <<EOF>>                     { return symbol(sym.EOF); }


