package edu.laney.math55

import edu.laney.math55.grammar.{PropositionEvaluator, PropositionParser, PropositionUtils}

/**
  * Truth Table program entry point.
  *
  * Created by greg.temchenko on 2/8/17.
  */
object TruthTable {

  def main(args: Array[String]) = {
    // evaluate every argument as a separate proposition
    for (expressionStr <- args) {
      val table = getTruthTableString(expressionStr)
      print(table)
    }
  }

  // helper functions

  // TODO make it lazily evaluated
  def getTruthTableString(expressionStr: String):String = {
    // parse the expression
    val matchedOption = PropositionParser.parse(expressionStr)

    if (matchedOption.isDefined) {
      val buf = new StringBuilder
      buf.append("Truth-table for the proposition: " + expressionStr + "\n")

      // get all variables
      val syntaxTree = matchedOption.get

      val varsSet = PropositionUtils.getVariablesSet(syntaxTree)
      val varsList = varsSet.toList.sortWith { case (a,b) => a.varName.compareTo(b.varName)<0 }
      for (pVar <- varsList) {
        buf.append(f"${pVar.varName}%5s")
      }
      buf.append("    *\n")

      // generate all possible values
      val valuesRows = PropositionUtils.generateVariablesValues(varsSet)
      for (row <- valuesRows) {
        for (pVar <- varsList) {
          val value = getBoolString(row(pVar))
          buf.append(f"$value%5s")
        }

        // evaluate the proposition given generated values
        val value = PropositionEvaluator.evaluate(syntaxTree, row)
        val valueStr = getBoolString(value)
        buf.append(f"$valueStr%5s")
        buf.append("\n")
      }
      buf.toString()
    } else {
      "Error Occurred"
    }
  }

  def getBoolString(value: Boolean) = if (value) "T" else "F"

}













