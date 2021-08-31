/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.abstract_syntax_tree.instruction;

import dummy_classes.Case;
import interpreter.abstract_syntax_tree.environment.Environment;
import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author zofia-px
 */
public class Switch implements Instruction, Serializable {
    private Expression condition;
    private LinkedList<Case> caseList;
    private int row, column;

    public Switch(Expression condition, LinkedList<Case> caseList, int row, int column) {
        this.condition = condition;
        this.caseList = caseList;
        this.row = row;
        this.column = column;
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
