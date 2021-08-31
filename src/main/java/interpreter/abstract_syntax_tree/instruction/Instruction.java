/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.abstract_syntax_tree.instruction;

import interpreter.abstract_syntax_tree.environment.Environment;

/**
 *
 * @author zofia-px
 */
public interface Instruction {
    public int getRow();
    public int getColumn();
    public Object execute(Environment environment);
    public Object analyze(Environment environment);
}
