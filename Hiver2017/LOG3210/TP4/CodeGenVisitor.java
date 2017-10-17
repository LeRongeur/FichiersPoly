public class CodeGenVisitor implements ParserVisitor
{


  public Object visit(SimpleNode node, Object data) { return data; }

  public Object visit(ASTProgram node, Object data) { 
	data = node.childrenAccept(this, data);	
  System.out.println("12testing");
	return data;
  }

  public Object visit(ASTBlock node, Object data) {
	data = node.childrenAccept(this, data);
	return data; 
  }

  public Object visit(ASTStmt node, Object data) {
	data = node.childrenAccept(this, data);
	return data;  
  } 
  
  public Object visit(ASTAssignStmt node, Object data) {
	data = node.childrenAccept(this, data);
	return data; 
  }

  public Object visit(ASTIfStmt node, Object data) {
	data = node.childrenAccept(this, data);
	return data; 
  }

  public Object visit(ASTWhileStmt node, Object data) {
	data = node.childrenAccept(this, data);
	return data; 
  }
  
  public Object visit(ASTCompExpr node, Object data) { 
	data = node.childrenAccept(this, data);
	return data; 
  }

  public Object visit(ASTAndOrExpr node, Object data) { 
	data = node.childrenAccept(this, data);
	return data; 
  }
  
  public Object visit(ASTNotExpr node, Object data) { 
	data = node.childrenAccept(this, data);
	return data; 
  }
  
  public Object visit(ASTAddExpr node, Object data) {
	data = node.childrenAccept(this, data);
	return data; 
  }
  

  public Object visit(ASTMultExpr node, Object data) { 
	data = node.childrenAccept(this, data);
	return data; 
  }

  public Object visit(ASTPowExpr node, Object data) { 
	data = node.childrenAccept(this, data);
	return data; 
  }

  public Object visit(ASTNegExpr node, Object data) { 
	data = node.childrenAccept(this, data);
	return data; 
  }

  public Object visit(ASTBasicExpr node, Object data) {
	data = node.childrenAccept(this, data);
	return data; 
  }
  
  public Object visit(ASTRealValue node, Object data) {
	return data; 
  }

  public Object visit(ASTIntValue node, Object data) { 
	return data; 
  }

  public Object visit(ASTIdentifier node, Object data) {
	return data; 
  }
  public String gen(String operator, String operand1, String operand2)
  {

    System.out.println(operand1);
    return 
  }
  public String newTemp()
  {
    return "t" + (compteur++);
  }
}