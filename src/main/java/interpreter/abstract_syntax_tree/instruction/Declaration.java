/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.abstract_syntax_tree.instruction;

import dummy_classes.Array;
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
public class Declaration implements Instruction, Serializable {
    
    private EnumType type;
    private LinkedList<String> idList;
    private String id;
    private boolean doesKeep;
    private Expression expression;
    private LinkedList<Array> arraysDec;
    private LinkedList<LinkedList> arrayExpr;
    private int row, column;

    public Declaration(EnumType type, LinkedList<String> idList, Expression expression, int row, int column, boolean doesKeep) {
        this.type = type;
        this.idList = idList;
        this.expression = expression;
        this.row = row;
        this.column = column;
        this.doesKeep = doesKeep;
        this.id = null;
        this.arraysDec = null;
        this.arrayExpr = null;
    }

    public Declaration(EnumType type, String id, Expression expression, int row, int column, boolean doesKeep) {
        this.type = type;
        this.id = id;
        this.expression = expression;
        this.row = row;
        this.column = column;
        this.doesKeep = doesKeep;
        this.idList = null;
        this.arraysDec = null;
        this.arrayExpr = null;
    }

    public Declaration(EnumType type, LinkedList<Array> arraysDec, LinkedList<LinkedList> arrayExpr, int row, int column, boolean doesKeep) {
        this.type = type;
        this.arraysDec = arraysDec;
        this.arrayExpr = arrayExpr;
        this.row = row;
        this.column = column;
        this.doesKeep = doesKeep;
        this.id = null;
        this.idList = null;
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
        /*for(LinkedList<Object> declaration : this.declarations) {
            if(declaration == null) continue;
            Sym value = this.defaultValue();
            if(declaration.getLast() != null) {
                Expression expression = (Expression) declaration.getLast();
                value = (Sym) expression.execute(environment);
                if(value.getType() != this.type) {
                    environment.getErrors().add(new GlobalError(row, column, "semantico", value.getValue().toString(), "Tipo incorrecto en declaracion de variable."));
                    continue;
                }
            }
            if(!environment.insert((String) declaration.getFirst(), value)) {
                environment.getErrors().add(new GlobalError(row, column, "semantico", (String) declaration.getFirst(), "El identificador de variable ya existe en el ambito"));
            }
        }*/
        return null;
    }
    
    private Sym defaultValue() {
        switch(this.type) {
            case entero:
                return new Sym(EnumType.entero, 0);
            case doble:
                return new Sym(EnumType.doble, 0);
            case booleano:
                return new Sym(EnumType.booleano, false);
            case cadena:
                return new Sym(EnumType.cadena, "");
            default:
                return new Sym(EnumType.caracter, ' ');
        }
    } 

    @Override
    public Object analyze(Environment environment) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
