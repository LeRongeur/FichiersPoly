/* Generated By:JJTree: Do not edit this line. ASTAssignStmt.java */

import java.util.Vector;	

public class ASTAssignStmt extends SimpleNode {
  public ASTAssignStmt(int id) {
    super(id);
  }

  public ASTAssignStmt(Parser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(ParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }

  //PLB
  private Vector ops = new Vector();
  public void addOp(String o) { ops.add(o); }
  public Vector getOps() { return ops; }
}
