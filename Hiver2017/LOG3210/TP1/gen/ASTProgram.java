/* Generated By:JJTree: Do not edit this line. ASTProgram.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTProgram extends SimpleNode {
  public ASTProgram(int id) {
    super(id);
  }

  public ASTProgram(LEParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(LEParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=9a25553f7ab1bf53f107cc35f7839f67 (do not edit this line) */
