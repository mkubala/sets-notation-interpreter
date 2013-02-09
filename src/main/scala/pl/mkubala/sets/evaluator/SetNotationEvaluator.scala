package pl.mkubala.sets.evaluator

import pl.mkubala.sets.SetExpression
import pl.mkubala.sets.SetDef
import pl.mkubala.sets.SetBinOp

class SetNotationEvaluator {

  type SetOperation = (Set[Int], Set[Int]) => Set[Int]

  private val operations = scala.collection.mutable.Map[String, SetOperation]()

  def addOperation(symbol: String, operation: SetOperation) =
    if (operations contains symbol)
      throw new IllegalStateException("Detected collision fo op symbol: '" + symbol + "'")
    else
      operations += (symbol -> operation)

  protected def getOperationForSymbol(opSymbol: String): SetOperation =
    operations getOrElse (opSymbol, throw new IllegalArgumentException("Unsupported operation: '" + opSymbol + "'"))

  def eval(expressionTree: SetExpression): Set[Int] = expressionTree match {
    case SetDef(elements) => elements
    case SetBinOp(left, operator, right) => getOperationForSymbol(operator)(eval(left), eval(right))
    case _ => throw new IllegalArgumentException("Unvalid expression: " + expressionTree)
  }

}
