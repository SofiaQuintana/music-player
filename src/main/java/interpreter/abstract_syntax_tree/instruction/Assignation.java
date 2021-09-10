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
        if(array == null) {
            if(doesIncrease) { //Aumento
                return null;
            } else {
                Sym value = environment.search(id);
                if(value != null) {
                    Sym update = (Sym) expression.analyze(environment);
                    return dataTypeValueAssignment(environment, update, value);
                } else {
                    environment.getErrors().add(new GlobalError(row, column, "semantico", id, "La variable con el id " + id + " no existe en el ambito actual."));
                    return new Sym(EnumType.error, "@error");
                }
            }
        } else {
            return null; //Area de arrays
        }
    }
    
    private Object dataTypeValueAssignment(Environment environment, Sym finalValue, Sym variable) {
        if(ImplicitCast.doesHaveSameValueType(finalValue.getType(), finalValue.getValue())) {
            environment.updateValue(id, finalValue);
            return null;
        } else {
            Object temporalValue = ImplicitCast.implicitCasting(finalValue.getType(), finalValue.getValue());
            if(temporalValue == null) {
                String message = "No se pudo realizar el casteo implicito al valor " + finalValue.getValue().toString();
                environment.getErrors().add(new GlobalError(row, column, "semantico", finalValue.getValue().toString(), message));
                return new Sym(EnumType.error, "@error");
            } else {
                environment.updateValue(id, new Sym(finalValue.getType(), temporalValue));
                return null;
            }
        }
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
