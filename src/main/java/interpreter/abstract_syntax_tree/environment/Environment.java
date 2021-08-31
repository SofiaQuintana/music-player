/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.abstract_syntax_tree.environment;

import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author zofia-px
 */
public class Environment {
    private HashMap<String,Sym> table;
    private LinkedList<GlobalError> errors;
    final private Environment previous;

    public Environment(Environment previous, LinkedList<GlobalError> errors) {
        this.table = new HashMap<>();
        this.errors = errors;
        this.previous = previous;
    }

    /** *  Metodo encargado de evaluar si una variable se encuentra ingresada en un ambito en especifico, 
     * 1. En caso de que el nombre de la nueva variable ya exista en la tabla retorna un valor falso, por ende dicha variable no se agrega a la tabla.
     * 2. De lo contrario agrega la nueva variable a la tabla y retorna un valor verdadero.
     * @param name
     * @param var
     * @return 
    */
    public boolean insert(String name, Sym var) {
        //En caso
        if(table.containsKey(name)) return false;
        table.put(name, var);
        return true;
    }
    
    /** * Metodo encargado de realizar la busqueda de una variable en la tabla de simbolos, recorre cada uno de los 
     * ambitos padre (anteriores) al ambito en el que nos encontramos.
     * 1.Si encuentra la variable, retorna el valor.
     * 2. De lo contrario retorna un valor nulo.
     * @param name
     * @return 
    */
    public Sym search(String name) {
        for(Environment env = this; env != null; env = env.previous) {
            if(env.table.containsKey(name)) return env.table.get(name);
        }
        return null;
    }
    
    /** * Metodo encargado de realizar la actualizacion del valor de una variable, realiza la busqueda en todos
     * los ambitos padre del ambito presente, si encuentra el valor realiza la actualizacion.
     * @param name
     * @param var
     */
    public void updateValue(String name, Sym var) {
        for(Environment env = this; env != null; env = env.previous) {
            if(env.table.containsKey(name)) {
                env.table.replace(name, var);
                return;
            }
        }
    }

    public LinkedList<GlobalError> getErrors() {
        return errors;
    }
    
    
}
