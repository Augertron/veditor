/**
 * 
 * This file is based on the VHDL parser originally developed by
 * (c) 1997 Christoph Grimm,
 * J.W. Goethe-University Frankfurt
 * Department for computer engineering
 *
 **/
package net.sourceforge.veditor.parser.vhdl;



/* Generated By:JJTree: Do not edit this line. ASTcomponent_instantiation_statement.java */
/* JJT: 0.3pre1 */

public class ASTcomponent_instantiation_statement extends SimpleNode {
  ASTcomponent_instantiation_statement(int id) {
    super(id);
  }
  
  public String getIdentifier(){
	  return ((ASTidentifier)jjtGetChild(0)).name;
  }
  
  public ASTinstantiated_unit getInstatiatedUnit(){
	  return ((ASTinstantiated_unit)jjtGetChild(1));
  }
}
