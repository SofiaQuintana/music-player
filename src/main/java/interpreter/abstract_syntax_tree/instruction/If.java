/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.abstract_syntax_tree.instruction;

import interpreter.abstract_syntax_tree.environment.Environment;
import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author zofia-px
 */
public class If implements Instruction, Serializable {
    
    private Expression condition;
    private LinkedList<Instruction> instructions;
    private LinkedList<If> elseIfList;
    private If elseIns;
    private int row, column;

    public If(If elseIns, Expression condition, LinkedList<Instruction> instructions, LinkedList<If> elseIfList, int row, int column) {
        this.elseIns = elseIns;
        this.condition = condition;
        this.instructions = instructions;
        this.elseIfList = elseIfList;
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
