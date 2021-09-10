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
        try {
            
        } catch (Exception e) {
            environment.getErrors().add(new GlobalError(row, column, "de ejecucion", id, "excepcion no controlada en declaracion de variable"));
        }
        return null;
    }

    private Sym defaultValue() {
        switch(this.type) {
            case entero:
                return new Sym(EnumType.entero, null);
            case doble:
                return new Sym(EnumType.doble, null);
            case booleano:
                return new Sym(EnumType.booleano, null);
            case cadena:
                return new Sym(EnumType.cadena, null);
            default:
                return new Sym(EnumType.caracter, null);
        }
    } 

    @Override
    public Object analyze(Environment environment) {
        Sym operationValue = (this.expression != null) ? (Sym)expression.analyze(environment) : new Sym(EnumType.nulo, "@null");
        if(id != null) {
            return simpleDeclaration(environment, operationValue, this.id);
        } else {
            if(arraysDec != null) {
                //Area de declaracion de arreglos
            } else {
                multiDeclaration(environment, operationValue);
            }
        }
        return null;
    }

    private void multiDeclaration(Environment environment, Sym expression) {
        for(String temporal : idList) {
            simpleDeclaration(environment, expression, temporal);
        }
    }

    /**
     * Metodo encargado de manejar la declaracion de parametros de funciones
     * @param environment
     * @param expression
     * @return
     */
    private Sym simpleDeclaration(Environment environment, Sym expression, String identifier) {
        if(expression.getType() == EnumType.nulo) {
            if(environment.insert(identifier, defaultValue())) {
                return null;
            } else {
                environment.getErrors().add(new GlobalError(row, column, "semantico", identifier, "La variable " + identifier + " ya existe en el ambito actual."));
                return new Sym(EnumType.error, "@error");
            }
        } else {
            if(environment.insert(id, expression)) {
                return null;
            } else {
                environment.getErrors().add(new GlobalError(row, column, "semantico", identifier, "La variable " + identifier + " ya existe en el ambito actual."));
                return new Sym(EnumType.error, "@error");
            }
        }
    }
}
