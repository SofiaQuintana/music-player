/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dummy_classes;

import interpreter.abstract_syntax_tree.instruction.Expression;
import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author zofia-px
 */
public class Array implements Serializable {
    private int row, column;
    private String id;
    private LinkedList<Expression> dimensions;

    public Array(int row, int column, String id, LinkedList<Expression> dimensions) {
        this.row = row;
        this.column = column;
        this.id = id;
        this.dimensions = dimensions;
    }
    
    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }

    public String getId() {
        return id;
    }

    public LinkedList<Expression> getDimensions() {
        return dimensions;
    }

    
}
