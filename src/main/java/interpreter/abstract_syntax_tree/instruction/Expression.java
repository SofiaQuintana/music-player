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
    public Object analyze(Environment environment) {
        Sym leftSym = (this.left != null) ? (Sym)left.analyze(environment) : new Sym(EnumType.nulo, "@null");
        Sym rightSym = (this.right != null) ? (Sym)right.analyze(environment) : new Sym(EnumType.nulo, "@null");
        switch(type) {
            /* Data values */
            case integer:   return new Sym(EnumType.entero, (int) value);
            case doble:     return new Sym(EnumType.doble, (double) value);
            case string:    return new Sym(EnumType.cadena, value.toString());
            case character: return new Sym(EnumType.caracter, (char) value);
            case bool:      return new Sym(EnumType.booleano, (boolean) value);
            /* IDs */
            case id:        return verifyIdExistence(environment,value.toString());
            case array:
                //Verificacion de existencia de array
            /* Arithmetic and Relational operation */
            case sum: case rest: case multiplication: 
            case division: case modulo: case potencia:
            case equal: case different: 
            case greater_than: case greater_equal: 
            case smaller_than: case smaller_equal:
                return arithmeticAndRelationalOperationRules(environment, leftSym, rightSym);
            /* Logical operation */
            case and: case nand: case or: case nor: case xor:
                return logicalOperationRules(environment, leftSym, rightSym);
            /* Unary operation */
            case negative: case not: case is_null:
                return unaryOperationRules(environment, leftSym);
            /* Simplified Operation */
            case increase: case decrease: case simp_sum:
                return null;
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object execute(Environment environment) {
        return null;
        /*Sym leftSym = (this.left != null) ? (Sym)left.execute(environment) : new Sym(EnumType.nulo, "@null");
        Sym rightSym = (this.right != null) ? (Sym)right.execute(environment) : new Sym(EnumType.nulo, "@null");
        switch(type) {
            case sum: case rest: case multiplication: case division:
            case modulo: case potencia: case increase:
            case decrease: case negative: case equal:
            case different: case greater_than: case smaller_than:
            case greater_equal: case smaller_equal: case is_null:
            case and:
            case nand:
            case or:
            case nor:
            case xor:
            case not:
            case integer:   return new Sym(EnumType.entero, (int) value);
            case doble:     return new Sym(EnumType.doble, (double) value);
            case string:    return new Sym(EnumType.cadena, (String) value);
            case character: return new Sym(EnumType.caracter, (char) value);
            case bool:      return new Sym(EnumType.booleano, (boolean) value);
            case array:
            case id:
            default:        return new Sym(EnumType.error, "@error");
        }*/
    }

    private Sym addError(Environment environment, String errorMessage, String value, String errorType) {
        environment.getErrors().add(new GlobalError(row, column, errorType, value, errorMessage));
        return new Sym(EnumType.error, "@error");
    }

    private Sym verifyIdExistence(Environment environment, String id) {
        Sym temporalValue = environment.search(id);
        if(temporalValue != null) {
            return temporalValue;
        } else {
            //Agregar error semantico (la variable no existe en el ambito)
            String errorMessage =  "La variable " + id + " no existe en el ambito actual";
            return addError(environment, errorMessage, id, "semantico");
        }
    }

    private boolean verifyDivisionByZero(Object rightValue) {
        if(rightValue instanceof Integer) return ((int)rightValue == 0); 
        else if(rightValue instanceof Double) return ((double)rightValue == 0);
        else if(rightValue instanceof Character) return ((int)(char)rightValue == 0);
        else return true;
    }

    /*private Sym sameDataValueTypeOperation(Environment environment, Sym leftExpr, Sym rightExpr) {
        switch(leftExpr.getType()) {
            case entero:
                switch(type) {
                    case sum: return new Sym(EnumType.entero, ((int)leftExpr.getValue() + (int)rightExpr.getValue()));
                    case rest: return new Sym(EnumType.entero, ((int)leftExpr.getValue() - (int)rightExpr.getValue()));
                    case multiplication: return new Sym(EnumType.entero, ((int)leftExpr.getValue() * (int)rightExpr.getValue()));
                    case division: 
                        if(verifyDivisionByZero(rightExpr.getValue())) return addError(environment, "No se puede realizar una division entre 0", rightExpr.getValue().toString(), "semantico");
                        else return new Sym(EnumType.entero, ((int)leftExpr.getValue() / (int)rightExpr.getValue()));   
                    case modulo: return new Sym(EnumType.entero, ((int)leftExpr.getValue() % (int)rightExpr.getValue()));
                    case potencia: return new Sym(EnumType.entero, (Math.pow((int)leftExpr.getValue(), (int)rightExpr.getValue())));
                    default: return null; //Temporal, aun falta verificar el resto de operaciones
                }       
            case doble:
                switch(type) {
                    case sum: return new Sym(EnumType.doble, ((double)leftExpr.getValue() + (double)rightExpr.getValue()));
                    case rest: return new Sym(EnumType.doble, ((double)leftExpr.getValue() - (double)rightExpr.getValue()));
                    case multiplication: return new Sym(EnumType.doble, ((double)leftExpr.getValue() * (double)rightExpr.getValue()));
                    case division: 
                        if(verifyDivisionByZero(rightExpr.getValue())) return addError(environment, "No se puede realizar una division entre 0", rightExpr.getValue().toString(), "semantico");
                        else return new Sym(EnumType.doble, ((double)leftExpr.getValue() / (double)rightExpr.getValue()));   
                    case modulo: return addError(environment, "La operacion modulo solo permite valores enteros, mas no dobles", leftExpr.getValue().toString(), "semantico");
                    case potencia: return addError(environment, "La operacion potencia solo permite exponentes enteros, mas no del tipo doble", rightExpr.getValue().toString(), "semantico");
                    default: return null; //Temporal, aun falta verificar el resto de operaciones
                }
            case cadena:
                switch(type) {
                    case sum: return new Sym(EnumType.cadena, (leftExpr.getValue().toString() + rightExpr.getValue().toString()));
                    case rest: case multiplication: case division: case modulo: case potencia: 
                        addError(environment, "No se puede realizar la operacion aritmetica sobre un String", leftExpr.getValue().toString(), "semantico");
                        return addError(environment, "No se puede realizar la operacion aritmetica sobre un String", rightExpr.getValue().toString(), "semantico");
                    default: return null; //Temporal, aun falta verificar el resto de operaciones
                }
            case caracter:
                switch(type) {
                    case sum: return new Sym(EnumType.caracter, (char)((int)(char)leftExpr.getValue() + (int)(char)rightExpr.getValue()));
                    case rest: return new Sym(EnumType.caracter, (char)((int)(char)leftExpr.getValue() - (int)(char)rightExpr.getValue()));
                    case multiplication: return new Sym(EnumType.caracter, (char)((int)(char)leftExpr.getValue() * (int)(char)rightExpr.getValue()));
                    case division: 
                        if(verifyDivisionByZero(rightExpr.getValue())) return addError(environment, "No se puede realizar una division entre 0", rightExpr.getValue().toString(), "semantico");
                        else return new Sym(EnumType.caracter, (char)((int)(char)leftExpr.getValue() / (int)(char)rightExpr.getValue()));   
                    case modulo: return addError(environment, "La operacion modulo solo permite valores enteros, mas no caracteres", leftExpr.getValue().toString(), "semantico");
                    case potencia: return addError(environment, "La operacion potencia solo permite exponentes enteros, mas no del tipo caracter", rightExpr.getValue().toString(), "semantico");
                    default: return null; //Temporal, aun falta verificar el resto de operaciones
                }
            case booleano:
                Object leftValue = (Boolean)leftExpr.getValue() ? 1 : 0;
                Object rightValue = (Boolean)rightExpr.getValue() ? 1 : 0;
                switch(type) {
                    case sum: return new Sym(EnumType.booleano, (int)leftValue + (int)rightValue);
                    case rest: return new Sym(EnumType.booleano, (int)leftValue - (int)rightValue);
                    case multiplication: return new Sym(EnumType.booleano, (int)leftValue * (int)rightValue);
                    case division: 
                        if(verifyDivisionByZero(rightValue)) return addError(environment, "No se puede realizar una division entre 0", rightExpr.getValue().toString(), "semantico");
                        else return new Sym(EnumType.booleano, (int)leftValue / (int)rightValue);   
                    case modulo: return addError(environment, "La operacion modulo solo permite valores enteros, mas no booleanos", leftExpr.getValue().toString(), "semantico");
                    case potencia: return addError(environment, "La operacion potencia solo permite exponentes enteros, mas no del tipo booleano", rightExpr.getValue().toString(), "semantico");
                    default: return null; //Temporal, aun falta verificar el resto de operaciones
                }
            default: return null;
        }
    }*/

    private Sym arithmeticAndRelationalOperationRules(Environment environment, Sym leftExpr, Sym rightExpr) {
        /*if(ImplicitCast.verifyOperationSameDataValueType(leftExpr, rightExpr)) {
            return sameDataValueTypeOperation(environment, leftExpr, rightExpr);
        } else {*/
        switch(leftExpr.getType()) {
            case entero:
                switch(rightExpr.getType()) {
                    case entero: return integerCastingRulesInOperations(environment, (int)leftExpr.getValue(), (int)rightExpr.getValue());
                    case doble: return doubleCastingRulesInOperations(environment, (double)(int)leftExpr.getValue(), (double)rightExpr.getValue());
                    case cadena: return stringCastingRulesInOperations(environment, leftExpr.getValue(), rightExpr.getValue());
                    case caracter: return integerCastingRulesInOperations(environment, (int)leftExpr.getValue(), (int)(char)rightExpr.getValue());
                    case booleano:
                        Object rightValue = (Boolean) rightExpr.getValue() ? 1 : 0;
                        return integerCastingRulesInOperations(environment, (int)leftExpr.getValue(), (int)rightValue);
                    default: return null;
                }
            case doble:
                switch(rightExpr.getType()) {
                    case entero: return doubleCastingRulesInOperations(environment, (double)leftExpr.getValue(), (double)(int)rightExpr.getValue());
                    case doble: return doubleCastingRulesInOperations(environment, (double)leftExpr.getValue(), (double)rightExpr.getValue());
                    case cadena: return stringCastingRulesInOperations(environment, leftExpr.getValue(), rightExpr.getValue());
                    case caracter: return doubleCastingRulesInOperations(environment, (double)leftExpr.getValue(), (double)(char)rightExpr.getValue());
                    case booleano:
                        Object rightValue = (Boolean) rightExpr.getValue() ? 1 : 0;
                        return doubleCastingRulesInOperations(environment, (double)leftExpr.getValue(), (double)(int)rightValue);
                    default: return null;
                }
            case cadena:
                switch(rightExpr.getType()) {
                    case entero: case doble: case cadena: case caracter: case booleano: 
                        return stringCastingRulesInOperations(environment, leftExpr.getValue(), rightExpr.getValue());
                    default: return null;
                }
            case caracter:
                switch(rightExpr.getType()) {
                    case entero: return integerCastingRulesInOperations(environment, (int)(char)leftExpr.getValue(), (int)rightExpr.getValue());
                    case doble: return doubleCastingRulesInOperations(environment, (double)(char)leftExpr.getValue(), (double)rightExpr.getValue());
                    case caracter: return charCastingRulesInOperations(environment, (char)leftExpr.getValue(), (char)rightExpr.getValue());
                    case cadena: return stringCastingRulesInOperations(environment, leftExpr.getValue(), rightExpr.getValue());
                    case booleano:
                        Object rightValue = (Boolean) rightExpr.getValue() ? 1 : 0;
                        return charCastingRulesInOperations(environment, (char)leftExpr.getValue(), (char)(int)rightValue);
                    default: return null;
                }
            case booleano:
                Object leftValue = (Boolean) leftExpr.getValue() ? 1 : 0; 
                switch(rightExpr.getType()) {
                    case entero: return integerCastingRulesInOperations(environment, (int)leftValue, (int)rightExpr.getValue());
                    case doble: return doubleCastingRulesInOperations(environment, (double)(int)leftValue, (double)rightExpr.getValue());
                    case cadena: return stringCastingRulesInOperations(environment, leftExpr.getValue(), rightExpr.getValue());
                    case caracter: return charCastingRulesInOperations(environment, (char)(int)leftValue, (char)rightExpr.getValue());
                    case booleano: 
                        Object rightValue = (Boolean)rightExpr.getValue() ? 1 : 0;
                        return integerCastingRulesInOperations(environment, (int)leftValue, (int)rightValue);
                    default: return null;
                }
            default:    return null;
        }
        //}
    }

    private Sym logicalOperationRules(Environment environment, Sym leftExpr, Sym rightExpr) {
        if(ImplicitCast.verifyOperationSameDataValueType(leftExpr, rightExpr) && leftExpr.getType() == EnumType.booleano) {
            return booleanRulesInOperations(environment, (boolean)leftExpr.getValue(), (boolean)rightExpr.getValue());
        } else {
            String message = "No se puede realizar una operacion logica entre un valor izquierdo de tipo " + getTypeValue(leftExpr.getType()) + 
                         " y un valor derecho de tipo " + getTypeValue(rightExpr.getType());
            return addError(environment, message, leftExpr.getValue().toString(), "semantico");
        }
    }

    private Sym unaryOperationRules(Environment environment, Sym leftExpr) {
        switch(type) {
            case not:
                if(leftExpr.getType() == EnumType.booleano) {
                    return new Sym(EnumType.booleano, !((boolean)leftExpr.getValue()));
                } else {
                    String message = "No se puede operar una negacion con un valor de tipo " + getTypeValue(leftExpr.getType());
                    return addError(environment, message, leftExpr.getValue().toString(), "semantico");        
                }
            case negative: 
                if(leftExpr.getType() == EnumType.entero) {
                    return new Sym(EnumType.entero, (int)leftExpr.getValue() * -1);
                } else if(leftExpr.getType() == EnumType.doble) {
                    return new Sym(EnumType.doble, (double)leftExpr.getValue() * -1);
                } else {
                    String message = "No se puede operar un negativo con un valor de tipo " + getTypeValue(leftExpr.getType());
                    return addError(environment, message, leftExpr.getValue().toString(), "semantico");
                }
            case is_null:
                if(leftExpr.getType() != EnumType.nulo || leftExpr.getType() != EnumType.error) {
                    return null; //Mejorar esto, existen ambiguedades aun
                }
            default: return null;
        }
    }

    private Sym integerCastingRulesInOperations(Environment environment, Object leftValue, Object rightValue) {
        switch(type) {
            case sum:   return new Sym(EnumType.entero, (int)leftValue + (int)rightValue);
            case rest:  return new Sym(EnumType.entero, (int)leftValue - (int)rightValue);
            case multiplication: return new Sym(EnumType.entero, (int)leftValue * (int)rightValue);
            case division: 
                if(verifyDivisionByZero(rightValue)) return addError(environment, "No se puede realizar una division entre 0", rightValue.toString(), "semantico");
                return new Sym(EnumType.entero, (int)leftValue / (int)rightValue);
            case modulo: return new Sym(EnumType.entero, (int)leftValue % (int)rightValue);
            case potencia: return new Sym(EnumType.entero, (Math.pow((int)leftValue, (int)rightValue)));
            case equal: return new Sym(EnumType.booleano, (int)leftValue == (int)rightValue);
            case different: return new Sym(EnumType.booleano, (int)leftValue != (int)rightValue);
            case greater_than: return new Sym(EnumType.booleano, (int)leftValue > (int)rightValue);
            case greater_equal: return new Sym(EnumType.booleano, (int)leftValue >= (int)rightValue);
            case smaller_than: return new Sym(EnumType.booleano, (int)leftValue < (int)rightValue);
            case smaller_equal: return new Sym(EnumType.booleano, (int)leftValue <= (int)rightValue);
            default: return null;
        }
    }

    private Sym doubleCastingRulesInOperations(Environment environment, Object leftValue, Object rightValue) {
        switch(type) {
            case sum:   return new Sym(EnumType.doble, (double)leftValue + (double)rightValue);
            case rest:  return new Sym(EnumType.doble, (double)leftValue - (double)rightValue);
            case multiplication:return new Sym(EnumType.doble, (double)leftValue * (double)rightValue);
            case division: 
                if(verifyDivisionByZero(rightValue)) return addError(environment, "No se puede realizar una division entre 0", rightValue.toString(), "semantico");
                return new Sym(EnumType.doble, (double)leftValue / (double)rightValue);
            case modulo:
                String message = "La operacion modulo solo permite valores enteros, y se estan intentando operar valores decimales";
                addError(environment, message, leftValue.toString(), "semantico");
                return addError(environment, message, rightValue.toString(), "semantico");
            case potencia:
                return addError(environment, "La operacion potencia solo permite exponentes enteros, mas no de tipo decimal", rightValue.toString(), "semantico");
            case equal: return new Sym(EnumType.booleano, (double)leftValue == (double)rightValue);
            case different: return new Sym(EnumType.booleano, (double)leftValue != (double)rightValue);
            case greater_than: return new Sym(EnumType.booleano, (double)leftValue > (double)rightValue);
            case greater_equal: return new Sym(EnumType.booleano, (double)leftValue >= (double)rightValue);
            case smaller_than: return new Sym(EnumType.booleano, (double)leftValue < (double)rightValue);
            case smaller_equal: return new Sym(EnumType.booleano, (double)leftValue <= (double)rightValue);
            default: return null;
        }
    }

    private Sym stringCastingRulesInOperations(Environment environment, Object leftValue, Object rightValue) {
        switch (type) {
            case sum: return new Sym(EnumType.cadena, leftValue.toString() + rightValue.toString());
            case rest: case multiplication: case division: case modulo: case potencia:
                addError(environment, "No se puede realizar la operacion aritmetica sobre un String", leftValue.toString(), "semantico");
                return addError(environment, "No se puede realizar la operacion aritmetica sobre un String", rightValue.toString(), "semantico");
            case equal: return new Sym(EnumType.booleano, leftValue.toString().equals(rightValue.toString()));
            case different: return new Sym(EnumType.booleano, !(leftValue.toString().equals(rightValue.toString())));
            case greater_than: return new Sym(EnumType.booleano, leftValue.toString().length() > rightValue.toString().length());
            case greater_equal: return new Sym(EnumType.booleano, leftValue.toString().length() >= rightValue.toString().length());
            case smaller_than: return new Sym(EnumType.booleano, leftValue.toString().length() < rightValue.toString().length());
            case smaller_equal: return new Sym(EnumType.booleano, leftValue.toString().length() <= rightValue.toString().length());
            default: return null;
        }
    }

    private Sym charCastingRulesInOperations(Environment environment, Object leftValue, Object rightValue) {
        switch(type) {
            /* Arithmetic operations */
            case sum: return new Sym(EnumType.caracter, (char)leftValue + (char)rightValue);
            case rest: return new Sym(EnumType.caracter, (char)leftValue - (char)rightValue);
            case multiplication: return new Sym(EnumType.caracter, (char)leftValue * (char)rightValue);
            case division: 
                if(verifyDivisionByZero(rightValue)) return addError(environment, "No se puede realizar una division entre 0", rightValue.toString(), "semantico");
                else return new Sym(EnumType.caracter, (char)leftValue / (char)rightValue);   
            case modulo: 
                String message = "La operacion modulo solo permite valores enteros, y se estan intentando operar chars";
                addError(environment, message, leftValue.toString(), "semantico");
                return addError(environment, message, rightValue.toString(), "semantico");
            case potencia: return addError(environment, "La operacion potencia solo permite exponentes enteros, mas no del tipo caracter", rightValue.toString(), "semantico");
            case equal: return new Sym(EnumType.booleano, (char)leftValue == (char)rightValue);
            case different: return new Sym(EnumType.booleano, (char)leftValue != (char)rightValue);
            case greater_than: return new Sym(EnumType.booleano, (char)leftValue > (char)rightValue);
            case greater_equal: return new Sym(EnumType.booleano, (char)leftValue >= (char)rightValue);
            case smaller_than: return new Sym(EnumType.booleano, (char)leftValue < (char)rightValue);
            case smaller_equal: return new Sym(EnumType.booleano, (char)leftValue <= (char)rightValue);
            default: return null; //Temporal, aun falta verificar el resto de operaciones
        }
    }

    private Sym booleanRulesInOperations(Environment environment, Object leftValue, Object rightValue) {
        switch(type) {
            case and: return new Sym(EnumType.booleano, (boolean)leftValue && (boolean)rightValue);
            case nand: return new Sym(EnumType.booleano, !((boolean)leftValue && (boolean)rightValue));
            case or: return new Sym(EnumType.booleano, (boolean)leftValue || (boolean)rightValue);
            case nor: return new Sym(EnumType.booleano, !((boolean)leftValue || (boolean)rightValue));
            case xor: return new Sym(EnumType.booleano, (boolean)leftValue ^ (boolean)rightValue);
            default: return null;
        }
    }

    private String getTypeValue(EnumType valueType) {
        switch (valueType) {
            case entero: return "entero";
            case doble: return "decimal";
            case cadena: return "cadena";
            case caracter: return "caracter";
            case booleano: return "booleano";
            case error: return "error";
            case nulo: return "nulo";
            default: return "";
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
