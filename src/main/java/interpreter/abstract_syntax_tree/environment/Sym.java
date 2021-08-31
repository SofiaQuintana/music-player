/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.abstract_syntax_tree.environment;

/**
 *
 * @author zofia-px
 */
public class Sym {
    private EnumType type;
    private Object value;

    public Sym(EnumType type, Object value) {
        this.type = type;
        this.value = value;
    }

    public EnumType getType() {
        return type;
    }

    public void setType(EnumType type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        if(value != null) return value.toString();
        return "null";
    }
    
    
}
