/**
 * 
 * This file is based on the VHDL parser originally developed by
 * (c) 1997 Christoph Grimm,
 * J.W. Goethe-University Frankfurt
 * Department for computer engineering
 *
 **/
package net.sourceforge.veditor.parser.vhdl;



/* Generated By:JJTree: Do not edit this line. ASTpackage_declaration.java */
/* JJT: 0.3pre1 */

public class ASTpackage_declaration extends SimpleNode {
  ASTpackage_declaration(int id) {
    super(id);
  }
  
  public String getIdentifier(){
	  return ((ASTidentifier)jjtGetChild(0)).name;
  }
  
}