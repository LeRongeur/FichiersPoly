/* Generated By:JJTree: Do not edit this line. ASTIfStmt.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTIfStmt extends SimpleNode {
  public ASTIfStmt(int id) {
    super(id);
  }

  public ASTIfStmt(Parser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(ParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=32a575b68d377ffb6b2a45328eff7112 (do not edit this line) */