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
public class Track implements Serializable, Instruction {
    private String name;
    private int row, column;
    private LinkedList<String> extendsList;
    private LinkedList<Instruction> instructions;

    public Track(String name, int row, int column, LinkedList<String> extendsList, LinkedList<Instruction> instructions) {
        this.name = name;
        this.row = row;
        this.column = column;
        this.extendsList = extendsList;
        this.instructions = instructions;
    }

    public Track(String name, int row, int column, LinkedList<Instruction> instructions) {
        this.name = name;
        this.row = row;
        this.column = column;
        this.instructions = instructions;
        this.extendsList = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<String> getExtendsList() {
        return extendsList;
    }

    public void setExtendsList(LinkedList<String> extendsList) {
        this.extendsList = extendsList;
    }

    public LinkedList<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(LinkedList<Instruction> instructions) {
        this.instructions = instructions;
    }

    @Override
    public String toString() {
        //Modificar para imprimir todos los elementos directamente en el archivo binario.
        return "Pista{" + "name=" + name + '}';
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getColumn() {
        return column;
    }

    @Override
    public Object execute(Environment environment) {
        //Se ejecutan todas las intrucciones 
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object analyze(Environment environment) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
