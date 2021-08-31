/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.abstract_syntax_tree.instruction;

import dummy_classes.Array;
import interpreter.abstract_syntax_tree.environment.Environment;
import java.io.Serializable;

/**
 *
 * @author zofia-px
 */
public class Assignation implements Instruction, Serializable {
    private String id;
    private Expression expression;
    private boolean doesIncrease;
    private Array array;
    private int row, column;

    public Assignation(String id, Expression expression, boolean doesIncrease, int row, int column) {
        this.id = id;
        this.expression = expression;
        this.doesIncrease = doesIncrease;
        this.row = row;
        this.column = column;
        this.array = null;
    }

    public Assignation(Array array) {
        this.array = array;
        this.row = array.getRow();
        this.column = array.getColumn();
    }

    @Override
    public Object execute(Environment environment) {
        return null;
    }

    @Override
    public Object analyze(Environment environment) {
        return null;
    }
    
    @Override
    public int getRow() {
        return this.row;
    }

    @Override
    public int getColumn() {
        return this.column;
    }
    
}
