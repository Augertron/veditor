/**
 * 
 * This file is based on the VHDL parser originally developed by
 * (c) 1997 Christoph Grimm,
 * J.W. Goethe-University Frankfurt
 * Department for computer engineering
 *
 **/
package net.sourceforge.veditor.parser.vhdl;



/* Generated By:JJTree: Do not edit this line. SimpleNode.java */
/* JJT: 0.3pre1 */

public class SimpleNode implements Node, VhdlParserCoreConstants{
  protected Node parent;
  protected Node[] children;
  protected int id;
  protected Token first_token, last_token;
  protected VhdlParserCore m_Parser;

  public SimpleNode(int i) {
    id = i;
    children=null;
  }

  public void jjtOpen() {
  }

  public void jjtClose() {
  }
  
  public void jjtSetParent(Node n) { parent = n; }
  public Node jjtGetParent() { return parent; }

  public void jjtAddChild(Node n, int i) {
    if (children == null) {
      children = new Node[i + 1];
    } else if (i >= children.length) {
      Node c[] = new Node[i + 1];
      System.arraycopy(children, 0, c, 0, children.length);
      children = c;
    }
    children[i] = n;    
  }

  public void setFirstToken(Token token){
	  first_token=token;
  }
  
  public void setLastToken(Token token){
	last_token=token;  
  }
  
  public Token getFirstToken(){ return first_token;}
  public Token getLastToken(){ return last_token;}
  
  public Node jjtGetChild(int i) {
    return children[i];
  }

  public int jjtGetNumChildren() {
    return (children == null) ? 0 : children.length;
  }
  
  /* You can override these two methods in subclasses of SimpleNode to
     customize the way the node appears when the tree is dumped.  If
     your output uses more than one line you should override
     toString(String), otherwise overriding toString() is probably all
     you need to do. */

  public String toString() { return VhdlParserCoreTreeConstants.jjtNodeName[id]; }
  public String toString(String prefix) { return prefix + toString(); }

  /* Override this method if you want to customize how the node dumps
     out its children. */

  public void dump(String prefix) {
    System.out.println(toString(prefix));
    if (children != null) {
      for (int i = 0; i < children.length; ++i) {
	SimpleNode n = (SimpleNode)children[i];
	if (n != null) {
	  n.dump(prefix + " ");
	}
      }
    }
  }

  //
  // Added by Ali Ghorashi
  //===============================
  
  public ErrorHandler getErrorHandler(){	  
	  return m_Parser.getErrorHandler();
  }
  
  public void setParser(VhdlParserCore parser){
	  m_Parser=parser;
  }
  
  public SimpleNode getChild(int n){
	  if(children!=null && n < children.length && children[n] instanceof SimpleNode){
		  return (SimpleNode)children[n];
	  }
	  else {
		  return null;
	  }
  }  
  
  public int getChildCount(){
	  return jjtGetNumChildren();
  }
  /**
   * Start a new block with a new symbol table. The current table
   * is saved in the variable upper_symtab. 
   */
  public void newBlock()
  {
    SymbolTable new_symtab = new SymbolTable();
    new_symtab.upper_symtab = m_Parser.getSymbolTable();
    m_Parser.setSymbolTable(new_symtab);
  }

  /**
   * Start a new block wit a new symbol table. The current table
   * is saved in the variable upper_symtab. Furthermore, a first
   * symbol of type type and id id is added.
   */
  public void newBlock(String type, String id)
  {
    SymbolTable new_symtab = new SymbolTable();
    new_symtab.upper_symtab = m_Parser.getSymbolTable();
    m_Parser.setSymbolTable(new_symtab);
    m_Parser.getSymbolTable().addSymbol(new Symbol(id, DEFAULT));
  }

  /**
   * End a block: the current symbol table becomes the upper symbol
   * table.
   */
  public void endBlock()
  {
	  m_Parser.setSymbolTable(m_Parser.getSymbolTable().upper_symtab);
  }

 
  /**
   * Perform all semantic actions and checks, which are necessary
   */
  public void Check()
  {
  }

  /**
   * Report warnings, when SIWG Level 1 is violated
   */
  public void CheckSIWGLevel1()
  {
  }

  public int getId()
  {
    return id;
  }
  
}

