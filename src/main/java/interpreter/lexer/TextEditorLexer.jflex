package interpreter.lexer;

import java.io.*;
import javax.swing.text.Segment;   

import org.fife.ui.rsyntaxtextarea.*; 

%% // Area Break

%public
%class TextEditorTokenMaker
%extends AbstractJFlexCTokenMaker
%unicode
%type org.fife.ui.rsyntaxtextarea.Token

%state MULTILINECOMMENT

/* RegEx */
Letter = [A-Za-z]   
Digit = [0-9]   
AnyCharacterButDoubleQuoteOrBackSlash = ([^\\\"\n])   
NonSeparator = ([^\t\f\r\n\ \(\)\{\}\[\]\;\,\.\=\>\<\!\~\?\:\+\-\*\/\&\|\^\%\"\']|"#"|"\\")   
IdentifierStart = ({Letter}|"_")   
IdentifierPart = ({IdentifierStart}|{Digit})   
WhiteSpace = ([ \t\f]+)   
Character = ("'" [^]{1} "'") | ('##') | ('#t') | ('#n')
UnclosedCharLiteral = ([\'][^\'\n]*)   
ErrorCharLiteral = ({UnclosedCharLiteral}[\'])   
StringLiteral = ([\"]({AnyCharacterButDoubleQuoteOrBackSlash})*[\"])   
UnclosedStringLiteral = ([\"]([\\].|[^\\\"])*[^\"]?)   
ErrorStringLiteral = ({UnclosedStringLiteral}[\"])   
Doble = ({IntegerLiteral})+(".")({IntegerLiteral}{1,6})
MLCBegin = "<-"   
MLCEnd = "->"   
LineCommentBegin = ">>"   
   
IntegerLiteral = {Digit}+   
ErrorNumberFormat = ({IntegerLiteral}){NonSeparator}+   
   
Separator = ([\(\)\{\}\[\]])   
Separator2 = ([\;,.])   
   
Identifier = ({IdentifierStart}{IdentifierPart}*)

%{
    public TextEditorTokenMaker() {
    }
    
    private void addHyperLinkToken(int start, int end, int tokenType) {
        int temporal = start + offsetShift;
        addToken(zzBuffer, start,end, tokenType, temporal, true);
    }
    
    private void addToken(int tokenType) {   
        addToken(zzStartRead, zzMarkedPos-1, tokenType);   
    }

    private void addToken(int start, int end, int tokenType) {   
        int temporal = start + offsetShift;   
        addToken(zzBuffer, start,end, tokenType, temporal, false);   
    }   

    public void addToken(char[] array, int start, int end, int tokenType, int startOffset, boolean hyperlink) {   
        super.addToken(array, start,end, tokenType, startOffset, hyperlink);   
        zzStartRead = zzMarkedPos;   
    }

    public String[] getLineCommentStartAndEnd() {   
        return new String[] { "//", null };   
    }
    
    public Token getTokenList(Segment text, int initialTokenType, int startOffset) {   
        resetTokenList();   
        this.offsetShift = -text.offset + startOffset;   

        // Start off in the proper state.   
        int state = Token.NULL;   
        switch (initialTokenType) {   
            case Token.COMMENT_MULTILINE:   
                state = MULTILINECOMMENT;   
                start = text.offset;   
            break;   
            default:   
               state = Token.NULL;   
        }   
        s = text;   
        try {   
           yyreset(zzReader);   
           yybegin(state);   
           return yylex();   
        } catch (IOException ioe) {   
           ioe.printStackTrace();   
           return new TokenImpl();   
        }   
    }  

    private boolean zzRefill() {   
        return zzCurrentPos>=s.offset+s.count;   
    }

    public final void yyreset(Reader reader) {   
        zzBuffer = s.array;    
        zzStartRead = s.offset;   
        zzEndRead = zzStartRead + s.count - 1;   
        zzCurrentPos = zzMarkedPos = zzPushbackPos = s.offset;   
        zzLexicalState = YYINITIAL;   
        zzReader = reader;   
        zzAtBOL  = true;   
        zzAtEOF  = false;   
    }   

%}

%% // Area Break

<YYINITIAL> {
    /* RESERVED WORD */
    
    "Pista" | 
    "pista" |                           
    "Extiende" |
    "extiende" |                     
    "Keep" | 
    "keep" |                             
    "Verdadero" | 
    "verdadero" | 
    "True" | 
    "true" |
    "Falso" | 
    "falso" | 
    "False" | 
    "false" |       
    "Var" | 
    "var" |                               
    "Entero" | 
    "entero" |                         
    "Doble" | 
    "doble" |                           
    "Boolean" | 
    "boolean" |                      
    "Caracter" | 
    "caracter" |                     
    "Cadena" | 
    "cadena" |                         
    "Arreglo" |
    "arreglo" |                       
    "Si" | 
    "si" |                                 
    "Sino" | 
    "sino" |                             
    "Switch" | 
    "switch" |                         
    "Caso" | 
    "caso" |                            
    "Default" | 
    "default" |                      
    "Para" | 
    "para" |                           
    "Mientras" | 
    "mientras" |                   
    "Hacer" | 
    "hacer" |                          
    "Salir" | 
    "salir" |                           
    "Continuar" | 
    "continuar" |                   
    "Retorna" | 
    "retorna" |                      
    "Reproducir" | 
    "reproducir" |                
    "Esperar" | 
    "esperar" |                      
    "Ordenar" | 
    "ordenar" |                       
    "Ascendente" | 
    "ascendente" |                 
    "Descendente" | 
    "descendente" |               
    "Pares" | 
    "pares" |                           
    "Impares" | 
    "impares" |                      
    "Primos" | 
    "primos" |                         
    "Sumarizar" | 
    "sumarizar" |                   
    "Longitud" | 
    "longitud" |                     
    "Mensaje" | 
    "mensaje" |                       
    "Principal" | 
    "principal" |                   
    "Do" | 
    "do" |                                 
    "Re" |
    "re" |                                 
    "Mi" | 
    "mi" |                               
    "Fa" | 
    "fa" |                               
    "Sol" | 
    "sol" |                             
    "La" | 
    "la" |                                
    "Do#" | 
    "do#" |                              
    "Re#" | 
    "re#" |                             
    "Mi#" | 
    "mi#" |                             
    "Fa#" | 
    "fa#" |                             
    "Sol#" | 
    "sol#" |                            
    "La#" | 
    "la#" |                              
    "Si#" | 
    "si#"                           { addToken(Token.RESERVED_WORD); }                     

    /* OPERATOR */
    "=" |                                       
    "!" |                                              
    "==" |                                       
    "!=" |                                       
    ">" |                                       
    "<" |                                       
    ">=" |                                       
    "<=" |                                       
    "!!" |                                       
    "&&" |                                       
    "!&&" |                                      
    "||" |                                       
    "!||" |                                      
    "&|" |                                       
    "+" |                                        
    "-" |                                        
    "*" |                                        
    "%" |                                        
    "^" |                                        
    "+=" |                                       
    "++" |                                       
    "--"                            { addToken(Token.OPERATOR); }
    
    /* Regular Expression */
    ("'") ("#'") ("'")              { addToken(Token.LITERAL_CHAR); }
    {Character}                     { addToken(Token.LITERAL_CHAR); }
    {StringLiteral}                 { addToken(Token.LITERAL_STRING_DOUBLE_QUOTE); }
    {Identifier}                    { addToken(Token.IDENTIFIER); } 
    {IntegerLiteral}                { addToken(Token.LITERAL_NUMBER_DECIMAL_INT); }  
    {Doble}                         { addToken(Token.LITERAL_NUMBER_FLOAT); }

    {MLCBegin}                      { start = zzMarkedPos-2; yybegin(MULTILINECOMMENT); }   
    {LineCommentBegin}.*            { addToken(Token.COMMENT_EOL); addNullToken(); return firstToken; }
    {Separator}                     { addToken(Token.SEPARATOR); }   
    {Separator2}                    { addToken(Token.SEPARATOR); } 
    {WhiteSpace}                    { addToken(Token.WHITESPACE); }

    /* Error */
    {UnclosedCharLiteral}           { addToken(Token.ERROR_CHAR); addNullToken(); return firstToken; }   
    {ErrorCharLiteral}              { addToken(Token.ERROR_CHAR); }   
    {UnclosedStringLiteral}         { addToken(Token.ERROR_STRING_DOUBLE); addNullToken(); return firstToken; }   
    {ErrorStringLiteral}            { addToken(Token.ERROR_STRING_DOUBLE); }
    {ErrorNumberFormat}             { addToken(Token.ERROR_NUMBER_FORMAT); }
    \n | 
    <<EOF>>                         { addNullToken(); return firstToken; }
    .                               { addToken(Token.IDENTIFIER); }
}

<MULTILINECOMMENT> {
    [^\n-]+                         {}   
    {MLCEnd}                        { yybegin(YYINITIAL); addToken(start,zzStartRead+2-1, Token.COMMENT_MULTILINE); }   
    "-"                             {}   
    \n | 
    <<EOF>>                         { addToken(start,zzStartRead-1, Token.COMMENT_MULTILINE); return firstToken; }
}
