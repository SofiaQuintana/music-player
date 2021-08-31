/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.abstract_syntax_tree.instruction;

import dummy_classes.Array;
import interpreter.abstract_syntax_tree.environment.Environment;

/**
 *
 * @author zofia-px
 */
public class Expression implements Instruction{
    private ExpressionType type;
    private Expression left;
    private Expression right;
    private Object value;
    private Array array; 
    private int row, column;

    public Expression(ExpressionType type, Expression left, Expression right, Object value, int row, int column) {
        this.type = type;
        this.left = left;
        this.right = right;
        this.value = value;
        this.row = row;
        this.column = column;
        this.array = null;
    }

    public Expression(ExpressionType type, Expression left, Expression right, int row, int column) {
        this.type = type;
        this.left = left;
        this.right = right;
        this.row = row;
        this.column = column;
    }

    public Expression(ExpressionType type, Array array) {
        this.type = type;
        this.array = array;
        this.left = null;
        this.right = null;
        this.value = null;
        this.row = array.getRow();
        this.column = array.getColumn();
    }
    
    

    @Override
    public int getRow() {
        return this.row;
    }

    @Override
    public int getColumn() {
        return this.column;
    }

    @Override
    public Object execute(Environment environment) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object analyze(Environment environment) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
