/**
 * 
 * This file is based on the VHDL parser originally developed by
 * (c) 1997 Christoph Grimm,
 * J.W. Goethe-University Frankfurt
 * Department for computer engineering
 *
 **/
package net.sourceforge.veditor.parser.vhdl;



/* Generated By:JJTree: Do not edit this line. ASTinterface_constant_declaration.java */
/* JJT: 0.3pre1 */

public class ASTinterface_constant_declaration extends SimpleNode {
  ASTinterface_constant_declaration(int id) {
    super(id);
  }
  
  public String[] getIdentifierList(){
	  return ((ASTidentifier_list)jjtGetChild(0)).getIdentifierNames();
  }
  
  public String getSubType(){
	  ASTsubtype_indication subtype= (ASTsubtype_indication)jjtGetChild(1);
	  return subtype.getIdentifier();
  }
}
