/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.abstract_syntax_tree.instruction;

/**
 *
 * @author zofia-px
 */
public enum ExpressionType {
    sum,
    rest,
    multiplication,
    division, 
    modulo,
    potencia,
    simp_sum,
    increase,
    decrease,
    negative,
    integer,
    doble,
    string,
    character,        
    bool,
    array,
    id, 
    equal,
    different,
    greater_than,
    smaller_than,
    greater_equal,
    smaller_equal,
    is_null,
    and,
    nand,
    or,
    nor,
    xor, 
    not
}
