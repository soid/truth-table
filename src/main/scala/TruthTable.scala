import PropositionSyntax._

/**
  * Truth Table program entry point.
  *
  * Created by greg.temchenko on 2/8/17.
  */
object TruthTable {
  def main(args: Array[String]):Int = {
    val expressionStr = "( ((p1) v (p2)) v (p3) )"

    // parse the expression
    val matchedOption = parse(expressionStr)

    if (matchedOption.isDefined) {
      // get all variables
      val syntaxTree = matchedOption.get

      val varsSet = getVars(syntaxTree)
      println("vars:")
      println(varsSet)
      0
    } else 1
  }

  def parse(expressionStr: String):Option[Proposition] = {
    val o = new PropositionalLogicParser

    val matchedOption = o.parse(o.parenthesizedProposition, expressionStr.replaceAll("\\s+", "")) match {
      case o.Success(matched,_) => Some(matched)
      case fail: o.Failure =>
        println("FAILURE: " + fail)
        None
      case err: o.Error =>
        println("ERROR: " + err)
        None
    }

    matchedOption.asInstanceOf[Option[Proposition]]
  }

  def getVars(sytaxTree:Proposition, vars: Set[PropositionVar] = Set()) : Set[PropositionVar] = {
    sytaxTree match {
      case prop: PropositionVar => vars + prop
      case OperatorOr(p1, p2) => vars ++ getVars(p1) ++ getVars(p2)
      case OperatorAnd(p1, p2) => vars ++ getVars(p1) ++ getVars(p2)
      case OperatorNot(prop) => vars ++ getVars(prop)
    }
  }

}