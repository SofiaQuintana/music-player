package interpreter.lexer;

import java_cup.runtime.*;
import java.util.LinkedList;
import java.util.Stack;
import interpreter.abstract_syntax_tree.environment.GlobalError;
import interpreter.parser.sym;

%%// Area Break

%public
%class MusicLexer
%cup
%cupdebug
%line
%column

%state IDENTACION, STRING

/* RegEx */
Identifier = ([:jletter:] | "_")([:jletterdigit:] | "_")*
Int = 0 | [1-9][0-9]*
Doble = ({Int})+(".")({Int}{1,6})
Character = ("'" [^]{1} "'") | ('##') | ('#t') | ('#n')
LineTerminator = \r | \n| \r\n
WhiteSpace =  [ \f]
InputCharacter = [^\r\n]
Identation = \t | "    "

BasicCommentary = ">>" {InputCharacter}* {LineTerminator}?
TraditionalCommentary = ("<-" [^->]* ~"->") | ("<-" [^/]~ "->")
Commentary = {BasicCommentary} | {TraditionalCommentary}

%{
    LinkedList<GlobalError> errors;
    int tabNumber; /* numero de tabulaciones */
    int stackValue; /* valor temporal en el stack */
    int desDentVal; /* numero de desidentaciones */
    boolean flag = false; 
    StringBuilder string = new StringBuilder();
    Stack<Integer> stack = new Stack<Integer>();

    public MusicLexer(java.io.Reader in, LinkedList<GlobalError> errors) {
        this.zzReader = in;
        this.errors = errors;
    }
    
    private Symbol verifyStack() {
        if(!stack.isEmpty() && yycolumn==0){
            yypushback(yytext().length()); 
            stack.pop(); 
            print_token("dedent"); 
            return symbol(sym.DEDENT);
        }    
        return null;
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
    "Pista" | "pista"                           {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.PISTA, yytext()); 
                                                }
    "Extiende" | "extiende"                     {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.EXT, yytext()); 
                                                }
    "Keep" | "keep"                             {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.KEEP, yytext()); 
                                                }
    "Verdadero" | "verdadero" | "True" | "true" { print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.TRUE, yytext()); 
                                                }
    "Falso" | "falso" | "False" | "false"       {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.FALSE, yytext()); 
                                                }
    "Var" | "var"                               {   print_token(yytext()); 
                                                    verifyStack(); 
                                                    return symbol(sym.VAR, yytext()); }
    "Entero" | "entero"                         {   print_token(yytext()); 
                                                    verifyStack();  
                                                    return symbol(sym.INT, yytext()); }
    "Doble" | "doble"                           {   print_token(yytext()); 
                                                    verifyStack(); 
                                                    return symbol(sym.DOUBLE, yytext()); }
    "Boolean" | "boolean"                       {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.BOOL, yytext()); }
    "Caracter" | "caracter"                     {   print_token(yytext()); 
                                                    verifyStack();   
                                                    return symbol(sym.CHAR, yytext()); }
    "Cadena" | "cadena"                         {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.STRING, yytext()); }
    "Arreglo" | "arreglo"                       {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.ARR, yytext()); }
    "Si" | "si"                                 {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.SI, yytext()); }
    "Sino" | "sino"                             {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.SINO, yytext()); }
    "Switch" | "switch"                         {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.SWITCH, yytext()); }
    "Caso" | "caso"                             {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.CASE, yytext()); }
    "Default" | "default"                       {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.DEF, yytext()); }
    "Para" | "para"                             {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.PARA, yytext()); }
    "Mientras" | "mientras"                     {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.WHILE, yytext()); }
    "Hacer" | "hacer"                           {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.HACER, yytext()); }
    "Salir" | "salir"                           {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.SALIR, yytext()); }
    "Continuar" | "continuar"                   {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.CONT, yytext()); }
    "Retorna" | "retorna"                       {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.RETURN, yytext()); }
    "Reproducir" | "reproducir"                 {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.PLAY, yytext()); }
    "Esperar" | "esperar"                       {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.WAIT, yytext()); }
    "Ordenar" | "ordenar"                       {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.ORDER, yytext()); }
    "Ascendente" | "ascendente"                 {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.ASC, yytext()); }
    "Descendente" | "descendente"               {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.DESC, yytext()); }
    "Pares" | "pares"                           {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.PAR, yytext()); }
    "Impares" | "impares"                       {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.IMPAR, yytext()); }
    "Primos" | "primos"                         {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.PRIMOS, yytext()); }
    "Sumarizar" | "sumarizar"                   {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.SUM, yytext()); }
    "Longitud" | "longitud"                     {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.LONG, yytext()); }
    "Mensaje" | "mensaje"                       {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.MENSAJE, yytext()); }
    "Principal" | "principal"                   {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.PRINCIPAL, yytext()); }
    "Do" | "do"                                 {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.DO, yytext()); }    
    "Re" | "re"                                 {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.RE, yytext()); }
    "Mi" | "mi"                                 {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.MI, yytext()); }
    "Fa" | "fa"                                 {   print_token(yytext()); 
                                                    verifyStack(); 
                                                    return symbol(sym.FA, yytext()); }
    "Sol" | "sol"                               {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.SOL, yytext()); }
    "La" | "la"                                 {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.LA, yytext()); }
    "Do#" | "do#"                               {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.DO8, yytext()); }
    "Re#" | "re#"                               {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.RE8, yytext()); }
    "Mi#" | "mi#"                               {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.MI8, yytext()); }
    "Fa#" | "fa#"                               {   print_token(yytext()); 
                                                    verifyStack(); 
                                                    return symbol(sym.FA8, yytext()); }
    "Sol#" | "sol#"                             {   print_token(yytext()); 
                                                    verifyStack(); 
                                                    return symbol(sym.SOL8, yytext()); }
    "La#" | "la#"                               {   print_token(yytext()); 
                                                    verifyStack(); 
                                                    return symbol(sym.LA8, yytext()); }
    "Si#" | "si#"                               {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.SI8, yytext()); }

    /* SIMBOLOS */
    "="                                         {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.EQ, "="); }
    "!"                                         {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.NOT, "!"); }        
    "=="                                        {   print_token(yytext()); 
                                                    verifyStack(); 
                                                    return symbol(sym.SAME, "=="); }
    "!="                                        {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.NEQ, "!="); }
    ">"                                         {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.MAY, ">"); }
    "<"                                         {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.MEN, "<"); }
    ">="                                        {   print_token(yytext()); 
                                                    verifyStack(); 
                                                    return symbol(sym.MAYEQ, ">="); }
    "<="                                        {   print_token(yytext()); 
                                                    verifyStack(); 
                                                    return symbol(sym.MENEQ, "<="); }
    "!!"                                        {   print_token(yytext()); 
                                                    verifyStack(); 
                                                    return symbol(sym.NULO, "!!"); }
    "&&"                                        {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.AND, "&&"); }
    "!&&"                                       {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.NAND, "!&&"); }
    "||"                                        {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.OR, "||"); }
    "!||"                                       {   print_token(yytext()); 
                                                    verifyStack(); 
                                                    return symbol(sym.NOR, "!||"); }
    "&|"                                        {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.XOR, "&|"); }
    "+"                                         {   print_token(yytext()); 
                                                    verifyStack(); 
                                                    return symbol(sym.PLUS, "+"); }
    "-"                                         {   print_token(yytext()); 
                                                    verifyStack(); 
                                                    return symbol(sym.REST, "-"); }
    "*"                                         {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.MULT, "*"); }
    "/"                                         {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.DIV, "/"); }
    "%"                                         {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.MOD, "%"); }
    "^"                                         {   print_token(yytext()); 
                                                    verifyStack(); 
                                                    return symbol(sym.POT, "^"); }
    "+="                                        {   print_token(yytext()); 
                                                    verifyStack(); 
                                                    return symbol(sym.PLUSEQ, "+="); }
    "++"                                        {   print_token(yytext()); 
                                                    verifyStack(); 
                                                    return symbol(sym.PP, "++"); }
    "--"                                        {   print_token(yytext()); 
                                                    verifyStack(); 
                                                    return symbol(sym.RR, "--"); }
    "["                                         {   print_token(yytext()); 
                                                    verifyStack(); 
                                                    return symbol(sym.CA, "["); }
    "]"                                         {   print_token(yytext()); 
                                                    verifyStack(); 
                                                    return symbol(sym.CC, "]"); }
    "{"                                         {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.LLA, "{"); }
    "}"                                         {   print_token(yytext()); 
                                                    verifyStack();  
                                                    return symbol(sym.LLC, "}"); }
    "("                                         {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.PA, "("); }
    ")"                                         {   print_token(yytext()); 
                                                    verifyStack();  
                                                    return symbol(sym.PC, ")"); }
    ","                                         {   print_token(yytext()); 
                                                    verifyStack(); 
                                                    return symbol(sym.COMMA, ","); }
    ";"                                         {   print_token(yytext()); 
                                                    verifyStack();  
                                                    return symbol(sym.SEMICOLON, ";"); }
    \"                                          { string.setLength(0); yybegin(STRING); }
    
    /* EXPRESIONES REGULARES */
    {Identifier}                                {   print_token(yytext()); 
                                                    verifyStack(); 
                                                    return symbol(sym.ID, yytext()); }
    {Int}                                       {   print_token(yytext()); 
                                                    verifyStack(); 
                                                    return symbol(sym.ENTERO, Integer.parseInt(yytext())); }
    {Doble}                                     {   print_token(yytext()); 
                                                    verifyStack();
                                                    return symbol(sym.DOBLE, Double.parseDouble(yytext())); }
    {Character}                                 {   print_token(yytext()); 
                                                    verifyStack(); 
                                                    return symbol(sym.CARACTER, yytext()); }
    {Commentary}                                {   print_token("Comentario"); return symbol(sym.SL, "\n"); }
    {Identation}                                {   if(yycolumn == 0){
                                                        tabNumber=1;
							flag=true;
							yybegin(IDENTACION);
                                                    }
                                                }
    {LineTerminator}                            {   print_token("Salto de linea"); return symbol(sym.SL, "\n"); }
    {WhiteSpace}                                {   /*Do nothing*/ }
    .                                           {   print_token("Error: " + yytext()); add_error(yytext(), yyline+1, yycolumn+1, "simbolo " + yytext() + " no esperado."); }
    <<EOF>>                                     {    if(stack.isEmpty()){
                                                        return new Symbol(sym.EOF);
                                                    } else{
                                                        stack.pop();
                                                        print_token("dedent");
                                                        return symbol(sym.DEDENT);
                                                    } 
                                                }
}

<IDENTACION> {
    {Identation}                                {   tabNumber++; }
    .                                           {   if(stack.isEmpty()){
                                                        stackValue = 0;
                                                    } else { 
                                                        stackValue = stack.peek();
                                                    }
		
                                                    if(stackValue < tabNumber && flag==true){	
                                                        yybegin(YYINITIAL);
                                                        yypushback(1);
                                                        stack.push(tabNumber);
                                                        print_token("indent"); 
                                                        return symbol(sym.INDENT);
                                                    } else if (stackValue > tabNumber && !stack.isEmpty()){
                                                        flag = false;
                                                        yypushback(1);
                                                        stack.pop();
                                                        print_token("dedent");
							return symbol(sym.DEDENT);
                                                    } else if(stackValue == tabNumber ){
                                                        yybegin(YYINITIAL);
                                                        yypushback(1);
                                                    } else {
                					yybegin(YYINITIAL);
                                                        yypushback(1);
                                                        add_error("desidentacion", yyline+1, yycolumn+1, "desidentacion inconsistente");
                                                    }
                                                }

}

<STRING> {
    \"                                          { yybegin(YYINITIAL); print_token("recibi una cadena"); return symbol(sym.CADENA, string.toString()); }
    "#t" | "\t"                                 { string.append("\t"); }
    "#n" | "\n"                                 { string.append("\n"); }
    "#"\"                                       { string.append("\""); }
    "##"                                        { string.append("#"); }
    "\r"                                        { string.append("\r"); }
    [^\n\r\"#]+                                 { string.append(yytext()); }
}
   