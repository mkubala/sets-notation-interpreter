package pl.mkubala.sets.evaluator

import org.scalatest.FunSuite
import pl.mkubala.sets.SetDef
import pl.mkubala.sets.SetBinOp
import pl.mkubala.sets.SetExpression
import pl.mkubala.sets.SetBinOp

class BasicSetOperationsTest extends FunSuite {

  val evaluator = new SetNotationEvaluator with BasicSetOperations

  class EvaluatorResultAssertion(expr: SetExpression) {
    def ===(expectedResult: Set[Int]) {
      test("'" + expr + "' === '" + expectedResult + "'") {
        assert(evaluator.eval(expr) === expectedResult)
      }
    }
  }

  implicit def SetExpressionToEvalResAssertion(expr: SetExpression): EvaluatorResultAssertion = new EvaluatorResultAssertion(expr)

  val setDefEmpty = SetDef(Set())
  val setDef123 = SetDef(Set(1, 2, 3))
  val setDef345 = SetDef(Set(3, 4, 5))
  val setDef567 = SetDef(Set(5, 6, 7))

  SetBinOp(setDef123, "^", setDef345) === Set(3)

  SetBinOp(setDef123, "^", setDef567) === Set()

  SetBinOp(setDef123, "^", setDefEmpty) === Set()

  SetBinOp(setDef123, "v", setDef345) === Set(1, 2, 3, 4, 5)

  SetBinOp(setDef123, "v", setDef567) === Set(1, 2, 3, 5, 6, 7)

  SetBinOp(setDef123, "v", setDefEmpty) === Set(1, 2, 3)

  SetBinOp(setDef123, """\""", setDef345) === Set(1, 2)

  SetBinOp(setDef123, """\""", setDef567) === Set(1, 2, 3)

  SetBinOp(setDef123, """\""", setDefEmpty) === Set(1, 2, 3)

  SetBinOp(setDef123, "-", setDef345) === Set(1, 2, 4, 5)

  SetBinOp(setDef123, "-", setDef567) === Set(1, 2, 3, 5, 6, 7)

  SetBinOp(setDef123, "-", setDefEmpty) === Set(1, 2, 3)

}