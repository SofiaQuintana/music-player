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
public class For implements Instruction, Serializable {
    private Instruction assignation;
    private Expression condition;
    private Instruction instruction;
    private LinkedList<Instruction> instructions;
    private int row, column;

    public For(Instruction assignation, Expression condition, Instruction instruction, LinkedList<Instruction> instructions, int row, int column) {
        this.assignation = assignation;
        this.condition = condition;
        this.instruction = instruction;
        this.instructions = instructions;
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
