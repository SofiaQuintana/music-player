/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.abstract_syntax_tree.instruction;

import interpreter.abstract_syntax_tree.environment.EnumType;
import interpreter.abstract_syntax_tree.environment.Environment;
import interpreter.abstract_syntax_tree.environment.GlobalError;
import interpreter.abstract_syntax_tree.environment.Sym;

import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author zofia-px
 */
public class Function implements Instruction, Serializable {
    private EnumType type;
    private String id;
    private LinkedList<Declaration> paramList;
    private LinkedList<Instruction> instructions;
    private boolean doesKeep;
    private int row, column;

    public Function(EnumType type, String id, LinkedList<Declaration> paramList, LinkedList<Instruction> instructions, boolean doesKeep, int row, int column) {
        this.type = type;
        this.id = id;
        this.paramList = paramList;
        this.instructions = instructions;
        this.doesKeep = doesKeep;
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
        return null;
    }

    private Sym methodDefinition() {
    
    }

    private Sym paramDeclaration(Environment environment) {
        for (Declaration declaration : paramList) {
            Sym temporal = (Sym)declaration.analyze(environment);
            if(temporal != null) {
                environment.getErrors().add(new GlobalError(row, column, type, value, description))
            }
        }
    }

}
