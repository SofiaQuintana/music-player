/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user_interface.text_editor;

import java.awt.Color;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxScheme;
import org.fife.ui.rsyntaxtextarea.Token;

/**
 *
 * @author zofia-px
 */
public class SyntaxColorRules {
    private SyntaxScheme scheme;
    private final Color purple = new Color(102,0,102);
    private final Color blue = new Color(10,117,173);
    private final Color orange = new Color(255,165,0);
    private final Color green = new Color(0,128,0);
    
    public SyntaxColorRules(RSyntaxTextArea textArea) {
        this.scheme = textArea.getSyntaxScheme();
        SetColorRules();
    }

    /*Cambio de colores en base a las reglas establecidas, solo se toma en cuenta
        los tokens utilizados en el lexer*/
    private void SetColorRules() {
        scheme.getStyle(Token.RESERVED_WORD).foreground = blue;
        scheme.getStyle(Token.OPERATOR).foreground = Color.BLACK;
        scheme.getStyle(Token.LITERAL_CHAR).foreground = orange;
        scheme.getStyle(Token.LITERAL_STRING_DOUBLE_QUOTE).foreground = orange;
        scheme.getStyle(Token.IDENTIFIER).foreground = green;
        scheme.getStyle(Token.LITERAL_NUMBER_DECIMAL_INT).foreground = purple;
        scheme.getStyle(Token.LITERAL_NUMBER_FLOAT).foreground = purple;
        scheme.getStyle(Token.COMMENT_MULTILINE).foreground = Color.GRAY;
        scheme.getStyle(Token.COMMENT_EOL).foreground = Color.GRAY;
        scheme.getStyle(Token.SEPARATOR).foreground = Color.BLACK;
        scheme.getStyle(Token.ERROR_CHAR).foreground = Color.RED;
        scheme.getStyle(Token.ERROR_STRING_DOUBLE).foreground = Color.RED;
        scheme.getStyle(Token.ERROR_NUMBER_FORMAT).foreground = Color.RED;
        scheme.getStyle(Token.SEPARATOR).foreground = Color.BLACK;
    }
}
