/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user_interface.text_editor;

import org.fife.ui.rsyntaxtextarea.AbstractTokenMakerFactory;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.TokenMakerFactory;

/**
 *
 * @author zofia-px
 */
public class SyntaxLanguage {
    private AbstractTokenMakerFactory tokenMaker;
    private static final String key = "text/TrackLanguage";
    
    public SyntaxLanguage(RSyntaxTextArea textArea) {
        this.tokenMaker = (AbstractTokenMakerFactory) TokenMakerFactory.getDefaultInstance();
        this.tokenMaker.putMapping(key, "interpreter.lexer.TextEditorTokenMaker");
        textArea.setSyntaxEditingStyle(key);
    }
    
}
