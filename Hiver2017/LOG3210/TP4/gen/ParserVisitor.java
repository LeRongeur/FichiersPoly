/* Generated By:JavaCC: Do not edit this line. ParserVisitor.java Version 5.0 */
public interface ParserVisitor
{
  public Object visit(SimpleNode node, Object data);
  public Object visit(ASTProgram node, Object data);
  public Object visit(ASTBlock node, Object data);
  public Object visit(ASTStmt node, Object data);
  public Object visit(ASTAssignStmt node, Object data);
  public Object visit(ASTCompExpr node, Object data);
  public Object visit(ASTAndOrExpr node, Object data);
  public Object visit(ASTNotExpr node, Object data);
  public Object visit(ASTAddExpr node, Object data);
  public Object visit(ASTMultExpr node, Object data);
  public Object visit(ASTPowExpr node, Object data);
  public Object visit(ASTNegExpr node, Object data);
  public Object visit(ASTBasicExpr node, Object data);
  public Object visit(ASTIdentifier node, Object data);
  public Object visit(ASTIntValue node, Object data);
  public Object visit(ASTRealValue node, Object data);
  public Object visit(ASTWhileStmt node, Object data);
  public Object visit(ASTIfStmt node, Object data);
}
/* JavaCC - OriginalChecksum=b0849150fc7da810d0b47b0ee50c204f (do not edit this line) */
