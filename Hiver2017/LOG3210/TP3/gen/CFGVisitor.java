import java.util.*;

public class CFGVisitor implements ParserVisitor
{

	public int counter = 0;
	public Deque<String> block = new ArrayDeque<String>();

  public Object visit(SimpleNode node, Object data) { return data; }

  public Object visit(ASTProgram node, Object data) { 
	//Creation du graphe et des noeuds initial et terminal.
	System.err.println("Je suis Program");
	System.out.println("digraph g {");
	System.out.println("  \"block0\" [label=\"Exit\", shape=\"rectangle\"];");
	
	data = node.childrenAccept(this, data);
	System.out.println("  \"block" + (counter + 1) + "\" [label=\"Start\", shape=\"rectangle\"];");
	System.out.println("  \"block" + (counter + 1) + "\" -> \"block" + (counter) + "\";");
	//cette ligne est a titre d'exemple. Elle montre comment lier les blocs entre eux.
	//System.out.println("  \"block0\" -> \"block1\";");	
	
	System.out.println("}");
	
	return data; 
  }

  public Object visit(ASTBlock node, Object data) {
  	System.err.println("Je suis Block");
  	String chaine = "";
  	for(int i = 0; i < node.jjtGetNumChildren(); i++)
  	{
  		chaine += node.jjtGetChild(i).jjtAccept(this, chaine).toString() + "\n"; // ou chaine +=
  		
  	}
  	System.out.println("  \"block" + (counter + 1) + "\" [label=\" " + chaine + " \", shape=\"rectangle\"];");
	//System.out.println("  \"block" + (counter + 1) + "\" -> \"block" + (counter) + "\";");
	counter++;
	return data; 
  }

  public Object visit(ASTStmt node, Object data) {
  	System.err.println("Je suis Stmt");
	return node.jjtGetChild(0).jjtAccept(this, data).toString();
  } 
  
  public Object visit(ASTAssignStmt node, Object data) {
  	System.err.println("Je suis AssignStmt");

	String chaine =  node.jjtGetChild(0).jjtAccept(this, data).toString();
  	for(int i = 1; i < node.jjtGetNumChildren() ; i++)
  	{
  		chaine += node.getOp() + node.jjtGetChild(i).jjtAccept(this, chaine).toString();
  	}
  	System.err.println(chaine);
	return chaine;
  }

  public Object visit(ASTIOStmt node, Object data) { 
  	System.err.println("Je suis IOStmt");
	data = node.childrenAccept(this, data);
	//block.push(node.getOp());
	return data; 
  }

  public Object visit(ASTIfStmt node, Object data) {
  	System.err.println("Je suis IfStmt");
  	String chaine = "if " + node.jjtGetChild(0).jjtAccept(this, data).toString() + " : ";
	for(int i = node.jjtGetNumChildren()-1; i > 0; i--)
	{
		chaine += node.jjtGetChild(i).jjtAccept(this, "").toString();
		/*System.out.println("  \"block" + (counter + 1) + "\" [label=\" " + chaine + " \", shape=\"rectangle\"];");*/
		System.out.println("  \"block" + (counter + i) + "\" -> \"block" + (counter) + "\";");
		System.out.println("  \"block" + (counter) + "\" -> \"block" + (counter + i - node.jjtGetNumChildren()) + "\";");
		/*System.err.println((counter + 4 - i));
		System.err.println((counter - i + 3));
		System.err.println(counter);
		System.err.println(i);*/
	}
	
	return chaine; 
  }

  public Object visit(ASTWhileStmt node, Object data) {
  	System.err.println("Je suis WhileStmt");
	data = node.childrenAccept(this, data);
	return data; 
  }
  
  public Object visit(ASTCompExpr node, Object data) {
  	System.err.println("Je suis CompExpr");
  	String chaine = node.jjtGetChild(0).jjtAccept(this, data).toString();
  	for(int i = 1; i < node.jjtGetNumChildren(); i++)
  	{
  		chaine += node.getOps().get(i - 1) + node.jjtGetChild(i).jjtAccept(this, chaine).toString();
  	}
	return chaine;
  }

  public Object visit(ASTAndOrExpr node, Object data) { 
  	System.err.println("Je suis AndOrExpr");
  	String chaine = node.jjtGetChild(0).jjtAccept(this, data).toString();
  	for(int i = 1; i < node.jjtGetNumChildren(); i++)
  	{
  		chaine += node.getOps().get(i - 1) + node.jjtGetChild(i).jjtAccept(this, chaine).toString();
  	}
	return chaine;
  }
  
  public Object visit(ASTNotExpr node, Object data) { 
  	System.err.println("Je suis NotExpr");
  	if(node.jjtGetNumChildren() > 0)
  	{
		return node.getOps().get(0) + node.jjtGetChild(0).jjtAccept(this, data).toString(); 
	}
	return data;
  }
  
  public Object visit(ASTAddExpr node, Object data) {
  	System.err.println("Je suis AddExpr");
  	String chaine = node.jjtGetChild(0).jjtAccept(this, data).toString();
  	for(int i = 1; i < node.jjtGetNumChildren(); i++)
  	{
  		chaine += node.getOps().get(i - 1) + node.jjtGetChild(i).jjtAccept(this, chaine).toString();
  	}
	return chaine;
  }
  

  public Object visit(ASTMultExpr node, Object data) {
  	System.err.println("Je suis MultExpr");
  	String chaine = node.jjtGetChild(0).jjtAccept(this, data).toString();
  	for(int i = 1; i < node.jjtGetNumChildren(); i++)
  	{
  		chaine += node.getOps().get(i - 1) + node.jjtGetChild(i).jjtAccept(this, chaine).toString();
  	}
	return chaine;
  }

  public Object visit(ASTPowExpr node, Object data) {
  	System.err.println("Je suis PowExpr");
  	String chaine =  node.jjtGetChild(0).jjtAccept(this, data).toString();
  	for(int i = 1; i < node.jjtGetNumChildren(); i++)
  	{
  		chaine += node.getOps().get(i - 1) + node.jjtGetChild(i).jjtAccept(this, chaine).toString();
  	}
	return chaine;
  }

  public Object visit(ASTNegExpr node, Object data) { 
  	System.err.println("Je suis NegExpr");
	// vector to string // + node.getchild.accept
	if(node.jjtGetNumChildren() > 0)
  	{
		return node.getOps().get(0) + node.jjtGetChild(0).jjtAccept(this, data.toString()).toString(); 
	}
	return data;
  }

  public Object visit(ASTBasicExpr node, Object data) {
  	System.err.println("Je suis BasicExpr");
  	if(node.jjtGetNumChildren() > 0)
		return node.jjtGetChild(0).jjtAccept(this, data.toString());
	return data; 
  }

  public Object visit(ASTFctStmt node, Object data) {
  	System.err.println("Je suis FctStmt");
	data = node.childrenAccept(this, data.toString());
	return data; 
  }
  
  public Object visit(ASTRealValue node, Object data) {
  	System.err.println("Je suis RealValue");
	return Double.toString(node.getValue()); 
  }

  public Object visit(ASTIntValue node, Object data) { 
  	System.err.println("Je suis IntValue");
	return Integer.toString(node.getValue()); 
  }

  public Object visit(ASTIdentifier node, Object data) {
  	System.err.println("Je suis Identifier");
	return node.getValue(); 
  }
}