package interpreter.abstract_syntax_tree.instruction;

import interpreter.abstract_syntax_tree.environment.EnumType;
import interpreter.abstract_syntax_tree.environment.Sym;

public class ImplicitCast {

    public static boolean doesHaveSameValueType(EnumType type, Object value) {
        EnumType temporal = EnumType.nulo; //null value by default 
        
        /* Designated value depends on value instance */
        if(value instanceof Integer) temporal = EnumType.entero; //integer value
        else if(value instanceof Double) temporal = EnumType.doble; //double value 
        else if(value instanceof String) temporal = EnumType.cadena; //string value
        else if(value instanceof Character) temporal = EnumType.caracter; //char value
        else if(value instanceof Boolean) temporal = EnumType.booleano; //boolean value
        return (temporal == type); //returns eq between both types 
    } 

    public static boolean verifyOperationSameDataValueType(Sym left, Sym right) {
        return (left.getType() == right.getType()); 
    }

    public static Object implicitCasting(EnumType type, Object value) {
        switch(type) {
            case entero:
                if(value instanceof Double)          return (int)(double)value;
                else if(value instanceof Boolean)    return (boolean)value ? 1 : 0;
                else if(value instanceof Character)  return (int)(char)value;
                else return null;
            case doble:
                if(value instanceof Integer)         return (double)(int)value;
                else return null;
            case cadena:
                return value.toString();
            case caracter:
                if(value instanceof Integer)          return (char)(int)value;
                else return null;
            case booleano: default:        
            return null;
        }
    }
}
