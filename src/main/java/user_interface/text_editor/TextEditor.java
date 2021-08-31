/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user_interface.text_editor;

import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

/**
 *
 * @author zofia-px
 */
public class TextEditor extends JTextArea {
    private SyntaxColorRules rules;
    private SyntaxLanguage language;
    private RSyntaxTextArea textArea;
    private RowCounter counter;
    private JPanel panel;
    private JScrollPane scrollPanel;

    public TextEditor(JPanel panel, String content) {
        setTextContent(content);
        this.panel = panel;
        this.rules = new SyntaxColorRules(textArea);
        this.language = new SyntaxLanguage(textArea);
        this.scrollPanel = new JScrollPane(textArea);
        this.counter = new RowCounter(textArea);
        setScrollPanel();
    }
    
    private void setTextContent(String content) {
        if(content == null) 
            this.textArea = new RSyntaxTextArea();
        else 
            this.textArea = new RSyntaxTextArea(content);
    }
    
    private void setScrollPanel() {
        scrollPanel.setRowHeaderView(counter);
        panel.setLayout(new GridLayout());
        panel.add(scrollPanel);
        textArea.setCodeFoldingEnabled(true);
    }
    
    /* returns text area input text */
    public String getTextContent() {
        return this.getText();
    }
}
