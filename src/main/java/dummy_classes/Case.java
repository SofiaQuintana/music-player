/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dummy_classes;

import interpreter.abstract_syntax_tree.instruction.Expression;
import interpreter.abstract_syntax_tree.instruction.Instruction;
import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author zofia-px
 */
public class Case implements Serializable {
    private Expression operation;
    private LinkedList<Instruction> instructions;
    private int row, column;

    public Case(Expression operation, LinkedList<Instruction> instructions, int row, int column) {
        this.operation = operation;
        this.instructions = instructions;
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }
    
}
