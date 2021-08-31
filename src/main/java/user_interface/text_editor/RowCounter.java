/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user_interface.text_editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyleConstants;
import javax.swing.text.Utilities;

/**
 *
 * @author zofia-px
 */
public class RowCounter extends JPanel implements CaretListener, DocumentListener, PropertyChangeListener {
     public final static float CENTER = 0.5f;
    public final static float RIGHT = 1.0f;
    private Color currentColor = new Color(105,105,105);
    private final static Border OUTER = new MatteBorder(0, 0, 0, 2,new Color(192,214,228));
    private final static int HEIGHT = Integer.MAX_VALUE - 1000000;
    private JTextComponent component;
    private boolean fontUpdated;
    private int borderGap;
    private Color currentLineForeground;
    private float alignment;
    private int minimumDisplayDigits;
    private int latestDigit;
    private int lastHeight;
    private int lastLine;
    private HashMap<String, FontMetrics> fonts;
    
    public RowCounter(JTextComponent component) {
        this(component, 3);
    }
    
    public RowCounter(JTextComponent component, int minimunDisplayDigits) {
        this.component = component;
        setFont(component.getFont());
        setBorderGap(5);
        setCurrentLineForeground(currentColor);
        setAlignment(RIGHT);
        setMinimumDisplayDigits(minimumDisplayDigits);
        component.getDocument().addDocumentListener(this);
        component.addCaretListener(this);
        component.addPropertyChangeListener("font", this);
        this.setBackground(new Color(192,214,228));
    }

    public boolean isFontUpdated() {
        return fontUpdated;
    }

    public void setFontUpdated(boolean fontUpdated) {
        this.fontUpdated = fontUpdated;
    }

    public int getBorderGap() {
        return borderGap;
    }

    public void setBorderGap(int borderGap) {
        this.borderGap = borderGap;
        Border inner = new EmptyBorder(0, borderGap, 0, borderGap);
        setBorder(new CompoundBorder(OUTER, inner));
        latestDigit = 0;
        setPreferredWidth();
    }

    public Color getCurrentLineForeground() {
        return currentLineForeground == null ? getForeground() : currentLineForeground;
    }

    public void setCurrentLineForeground(Color currentLineForeground) {
        this.currentLineForeground = currentLineForeground;
    }

    public float getAlignment() {
        return alignment;
    }

    public void setAlignment(float alignment) {
        this.alignment = alignment > 1.0f ? 1.0f : alignment < 0.0f ? -1.0f : alignment;
    }

    public int getMinimumDisplayDigits() {
        return minimumDisplayDigits;
    }

    public void setMinimumDisplayDigits(int minimumDisplayDigits) {
        this.minimumDisplayDigits = minimumDisplayDigits;
        setPreferredWidth();
    }

    public void setPreferredWidth() {
        Element root = component.getDocument().getDefaultRootElement();
        int lines = root.getElementCount();
        int digit = Math.max(String.valueOf(lines).length(), minimumDisplayDigits);
        if(latestDigit != digit) {
            latestDigit = digit;
            FontMetrics metrics = getFontMetrics(getFont());
            int width = metrics.charWidth('0') * digit;
            Insets insets = getInsets();
            int preferredWidth = insets.left + insets.right + width;
            Dimension dimension = getPreferredSize();
            dimension.setSize(preferredWidth, HEIGHT);
            setPreferredSize(dimension);
            setSize(dimension);
        }
    }
    
    @Override 
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        FontMetrics metrics = component.getFontMetrics(component.getFont());
        Insets insets = getInsets();
        int actualWidth = getSize().width - insets.left - insets.right;
        Rectangle clip = graphics.getClipBounds();
        int rowStartOffset = component.viewToModel(new Point(0, clip.y));
        int endOffset = component.viewToModel(new Point(0, clip.y + clip.height));
        while (rowStartOffset <= endOffset) {
            try {
                if (isCurrentLine(rowStartOffset)) {
                    graphics.setColor(getCurrentLineForeground());
                } else {
                    graphics.setColor(getForeground());
                }
                String lineNumber = getTextLineNumber(rowStartOffset);
                int stringWidth = metrics.stringWidth(lineNumber);
                int x = getOffsetX(actualWidth, stringWidth) + insets.left;
                int y = getOffsetY(rowStartOffset, metrics);
                graphics.drawString(lineNumber, x, y);
                rowStartOffset = Utilities.getRowEnd(component, rowStartOffset) + 1;
            } 
            catch (Exception e) {
                break;
            }
        }
    }
    
    private boolean isCurrentLine(int rowStartOffset) {
        int caretPosition = component.getCaretPosition();
        Element root = component.getDocument().getDefaultRootElement();
        if(root.getElementIndex(rowStartOffset) == root.getElementIndex(caretPosition)) return true;
        else return false;            
    }
    
    protected String getTextLineNumber(int rowStartOffset) {
        Element root = component.getDocument().getDefaultRootElement();
        int index = root.getElementIndex(rowStartOffset);
        Element line = root.getElement(index);
        if(line.getStartOffset() == rowStartOffset) return String.valueOf(index + 1);
        else return "";
    }

    private int getOffsetX(int actualWidth, int stringWidth) {
        return (int) ((actualWidth - stringWidth) * alignment);
    }
            
     private int getOffsetY(int rowStartOffset, FontMetrics fontMetrics)throws BadLocationException {
        //  Get the bounding rectangle of the row
        Rectangle r = component.modelToView(rowStartOffset);
        int lineHeight = fontMetrics.getHeight();
        int y = r.y + r.height;
        int descent = 0;

        //  The text needs to be positioned above the bottom of the bounding
        //  rectangle based on the descent of the font(s) contained on the row.
        if (r.height == lineHeight) // default font is being used
        {
            descent = fontMetrics.getDescent();
        } else // We need to check all the attributes for font changes
        {
            if (fonts == null) {
                fonts = new HashMap<String, FontMetrics>();
            }

            Element root = component.getDocument().getDefaultRootElement();
            int index = root.getElementIndex(rowStartOffset);
            Element line = root.getElement(index);

            for (int i = 0; i < line.getElementCount(); i++) {
                Element child = line.getElement(i);
                AttributeSet as = child.getAttributes();
                String fontFamily = (String) as.getAttribute(StyleConstants.FontFamily);
                Integer fontSize = (Integer) as.getAttribute(StyleConstants.FontSize);
                String key = fontFamily + fontSize;

                FontMetrics fm = fonts.get(key);

                if (fm == null) {
                    Font font = new Font(fontFamily, Font.PLAIN, fontSize);
                    fm = component.getFontMetrics(font);
                    fonts.put(key, fm);
                }

                descent = Math.max(descent, fm.getDescent());
            }
        }

        return y - descent;
    }
     
      @Override
    public void caretUpdate(CaretEvent e) {
        int caretPosition = component.getCaretPosition();
        Element root = component.getDocument().getDefaultRootElement();
        int currentLine = root.getElementIndex(caretPosition);

        if (lastLine != currentLine) {
            repaint();
            lastLine = currentLine;
        }
    }

//
//  Implement DocumentListener interface
//
    @Override
    public void changedUpdate(DocumentEvent e) {
        documentChanged();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        documentChanged();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        documentChanged();
    }

    private void documentChanged() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    int endPos = component.getDocument().getLength();
                    Rectangle rect = component.modelToView(endPos);

                    if (rect != null && rect.y != lastHeight) {
                        setPreferredWidth();
                        repaint();
                        lastHeight = rect.y;
                    }
                } catch (BadLocationException ex) {
                    /* nothing to do */ }
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getNewValue() instanceof Font) {
            if (fontUpdated) {
                Font newFont = (Font) evt.getNewValue();
                setFont(newFont);
                latestDigit = 0;
                setPreferredWidth();
            } else {
                repaint();
            }
        }
    }
}
