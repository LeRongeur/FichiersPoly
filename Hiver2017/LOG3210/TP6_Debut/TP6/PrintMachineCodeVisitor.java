import java.io.PrintWriter;
import java.io.IOException;
import java.util.*;

public class PrintMachineCodeVisitor implements ParserVisitor
{

  String m_outputFileName = null;
  PrintWriter m_writer = null;
  
  Integer nbRegisters = 3; // Changer selon le nombre de registres utilis/
  HashMap<String, String> registers = new HashMap<String, String>();

  Vector<Vector<String>> liveVariablesAtInstruction = new Vector<Vector<String>>();
  int outNodeCompteur = 0;

  public PrintMachineCodeVisitor(String outputFilename)  {
    m_outputFileName = outputFilename;
  }

  // Paramètre data: On ne transmet rien aux enfants
  // Valeur de retour: On ne retourne rien aux parents
  public Object visit(SimpleNode node, Object data) {
    return null;
  }

  // Paramètre data: On ne transmet rien aux enfants
  // Valeur de retour: On ne retourne rien aux parents
  public Object visit(ASTProgram node, Object data) {
    try{
      m_writer = new PrintWriter(m_outputFileName, "UTF-8");
    } catch (IOException e) {
      System.out.println("Failed to create ouput file.");
      return null;
    }

    for(int i = 0; i < nbRegisters; i++)
    {
      registers.put("R"+i, "");
    }

    // Visiter les enfants
    node.childrenAccept(this, null);

    m_writer.close();
  	return null;
  }

  // Paramètre data: On ne transmet rien aux enfants
  // Valeur de retour: On ne retourne rien aux parents
  public Object visit(ASTLive node, Object data) {
  	node.childrenAccept(this, null);
  	return null;
  }

  // Paramètre data: On ne transmet rien aux enfants
  // Valeur de retour: On ne retourne rien aux parents
  public Object visit(ASTLiveNode node, Object data) {

    // TODO:: Vous voulez probablement sauvegarder les lives pour utiliser dans les instructions...

  	node.childrenAccept(this, null);
  	return null;
  }

  // Paramètre data: On ne transmet rien aux enfants
  // Valeur de retour: On ne retourne rien aux parents
  public Object visit(ASTInNode node, Object data) {
  	node.childrenAccept(this, null);
  	return null;
  }

  // Paramètre data: On ne transmet rien aux enfants
  // Valeur de retour: On ne retourne rien aux parents
  public Object visit(ASTOutNode node, Object data) {
    liveVariablesAtInstruction.add(new Vector<String>());
    for(int i = 0; i < node.getLive().size(); i++)
    {
      liveVariablesAtInstruction.get(outNodeCompteur).add(node.getLive().get(i));
    }
  	node.childrenAccept(this, null);
    outNodeCompteur++;
  	return null;
  }

  // Paramètre data: On ne transmet rien aux enfants
  // Valeur de retour: On ne retourne rien aux parents
  public Object visit(ASTBlock node, Object data) {
  	node.childrenAccept(this, null);
  	return null;
  }

  // Paramètre data: On ne transmet rien aux enfants
  // Valeur de retour: On ne retourne rien aux parents
  public Object visit(ASTStmt node, Object data) {
    node.childrenAccept(this, null);
  	return null;
  }

  // Paramètre data: On ne transmet rien aux enfants
  // Valeur de retour: On ne retourne rien aux parents
  public Object visit(ASTAssignStmt node, Object data) {
    // On ne visite pas les enfants puisque l'on va manuellement chercher leurs valeurs
    // On n'a rien a transférer aux enfants
    String assigned = (String)node.jjtGetChild(0).jjtAccept(this, null);
    String left = (String)node.jjtGetChild(1).jjtAccept(this, null);
    String right = (String)node.jjtGetChild(2).jjtAccept(this, null);

    // TODO:: Chaque variable a son emplacement en mémoire, mais si elle est déjà
    // dans un registre, ne la rechargez pas!

    // TODO:: Si vos registres sont plein, déterminez quel variable que vous allez retirer
    // et si vous devez la sauvegarder!

    String res = "";
    String op1 = "";
    String op2 = "";

    // TODO : Devrait pas faire LD x, y quand on load le register du assigned, mais on devrait savoir quel registre utilise

    if(assigned.equals(left) && assigned.equals(right))
    {
      res = loadRegister(assigned);
      op1 = res;
      op2 = res;
    }
    else
    {
      if(assigned.equals(left))
      {
        res = loadRegister(assigned);
        op1 = res;
        op2 = loadRegister(right);
      }
      else if (assigned.equals(right))
      {
        res = loadRegister(assigned);
        op1 = loadRegister(left);
        op2 = res;
      }
      else if (left.equals(right))
      {
        res = loadRegister(assigned);
        op1 = loadRegister(left);
        op2 = op1;
      }
      else
      {
        res = loadRegister(assigned);
        op1 = loadRegister(left);
        op2 = loadRegister(right);
      }
    }

    char switched = node.getOp().charAt(0);
    switch (switched)
    {
      case '+': System.out.println("ADD " + res + ", " + op1 + ", " + op2);
      break;
      case '-': System.out.println("SUB " + res + ", " + op1 + ", " + op2);
      break;
      case '*': System.out.println("MULT " + res + ", " + op1 + ", " + op2);
      break;
      case '/': System.out.println("DIV " + res + ", " + op1 + ", " + op2);
      break;
      default: System.out.println("Error");
        break;
    }
    // TODO:: Écrivez la traduction en code machine, une instruction intermédiaire
    // peut générer plus qu'une instruction machine!

    return null;
  }

  // Paramètre data: On ne transmet rien aux enfants
  // Valeur de retour: On ne retourne rien aux parents
  public Object visit(ASTAssignUnaryStmt node, Object data) {
    // On ne visite pas les enfants puisque l'on va manuellement chercher leurs valeurs
    // On n'a rien a transférer aux enfants
    String assigned = (String)node.jjtGetChild(0).jjtAccept(this, null);
    String left = (String)node.jjtGetChild(1).jjtAccept(this, null);

    // TODO:: Chaque variable a son emplacement en mémoire, mais si elle est déjà
    // dans un registre, ne la rechargez pas!

    // TODO:: Si vos registres sont plein, déterminez quel variable que vous allez retirer
    // et si vous devez la sauvegarder!

    // TODO:: Écrivez la traduction en code machine, une instruction intermédiaire
    // peut générer plus qu'une instruction machine!

    m_writer.println(assigned + " = minus " + left);
    return null;
  }

  // Paramètre data: On ne transmet rien aux enfants
  // Valeur de retour: On ne retourne rien aux parents
  public Object visit(ASTAssignDirectStmt node, Object data) {
    // On ne visite pas les enfants puisque l'on va manuellement chercher leurs valeurs
    // On n'a rien a transférer aux enfants
    String assigned = (String)node.jjtGetChild(0).jjtAccept(this, null);
    String left = (String)node.jjtGetChild(1).jjtAccept(this, null);

    // TODO:: Chaque variable a son emplacement en mémoire, mais si elle est déjà
    // dans un registre, ne la rechargez pas!

    // TODO:: Si vos registres sont plein, déterminez quel variable que vous allez retirer
    // et si vous devez la sauvegarder!

    // TODO:: Écrivez la traduction en code machine, une instruction intermédiaire
    // peut générer plus qu'une instruction machine!
    return null;
  }

  // Paramètre data: On ne transmet rien aux enfants
  // Valeur de retour: on retourne son identifiant, qui est celui de l'enfant
  public Object visit(ASTExpr node, Object data) {
  	return node.jjtGetChild(0).jjtAccept(this, null);
  }

  // Paramètre data: On ne transmet rien aux enfants
  // Valeur de retour: On retourne l'indentifiant du noeud
  public Object visit(ASTIntValue node, Object data) {
    return String.valueOf(node.getValue());
  }

  // Paramètre data: On ne transmet rien aux enfants
  // Valeur de retour: On retourne l'indentifiant du noeud
  public Object visit(ASTIdentifier node, Object data) {
    return node.getValue();
  }

  public String loadRegister(String var)
  {
    String registreChoisi = "";
    // TODO : Checker si un register vide
    for(int i = 0; i < nbRegisters; i++)
    {
      if(registers.get("R"+i).equals(""))
      {
        registreChoisi = "R"+i;
        registers.put(registreChoisi, var);
        System.out.println("LD " + var + ", " + registreChoisi);
        return registreChoisi;
      }
    }

    // Checker si var est dans un registre
    for(int i = 0; i < nbRegisters; i++)
    {
      if(registers.get("R"+i).equals(var))
      {
        registreChoisi = "R"+i;
        System.out.println("LD " + var + ", " + registreChoisi);
        return registreChoisi;
      }
    }
    // Checker si le resultat est une operande de linstruction
    // Checker si var est live apres linstruction

    // Sinon, calculer score et spill le meilleur score
    // retourner le registre choisi
    System.out.println("LD " + var + ", " + registreChoisi);
    return registreChoisi;
  }
}
