import PropositionSyntax._

/**
  * Created by greg.temchenko on 2/10/17.
  */
object PropositionEvaluator {
  def evaluate(prop: Proposition, values: Map[PropositionVar, Boolean]): Boolean = {
    prop match {
      case pVar:PropositionVar => values(pVar)
      case OperatorOr(prop1, prop2) => evaluate(prop1, values) || evaluate(prop2, values)
      case OperatorAnd(prop1, prop2) => evaluate(prop1, values) && evaluate(prop2, values)
      case OperatorNot(propNot) => ! evaluate(propNot, values)
    }
  }
}
