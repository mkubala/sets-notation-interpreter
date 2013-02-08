package pl.mkubala.sets.evaluator

import org.scalatest.FunSuite

import pl.mkubala.sets.SetBinOp
import pl.mkubala.sets.SetDef

class SetNotationEvaluatorTest extends FunSuite {

  private val evaluator = new SetNotationEvaluator with TestSetOperations

  trait TestSetOperations {
    this: SetNotationEvaluator =>

    addOperation("union", (s1, s2) => s1 union s2)
    addOperation("intersect", (s1, s2) => s1 intersect s2)
  }

  // some helper constants
  val setDef123 = SetDef(Set(1, 2, 3))
  val setDef234 = SetDef(Set(2, 3, 4))
  val setDef345 = SetDef(Set(3, 4, 5))

  test("should eval empty SetDef to empty set") {
    // given
    val input = SetDef(Set())
    val expectedOutput = Set()

    // when
    val result = evaluator.eval(input);

    // then
    assert(result === expectedOutput)
  }

  test("should throw exception if unput expression tree is null") {
    // given
    val input = null

    // when & then
    intercept[IllegalArgumentException] {
      evaluator.eval(input)
    }
  }

  test("should throw exception if expression contains unsupported binaryOp") {
    // given
    val input = SetBinOp(setDef123, "someUnsupportedOp", setDef234)

    // when & then
    intercept[IllegalArgumentException] {
      evaluator.eval(input)
    }
  }

  test("should eval non-empty SetDef to non-empty set") {
    // given
    val input = setDef123
    val expectedOutput = Set(1, 2, 3)

    // when
    val result = evaluator.eval(input);

    // then
    assert(result === expectedOutput)
  }

  test("should eval 'SetBinOp(setDef123, \"union\", setDef345)' to 'Set(1, 2, 3, 4, 5)'") {
    // given
    val input = SetBinOp(setDef123, "union", setDef345)
    val expectedOutput = Set(1, 2, 3, 4, 5)

    // when
    val result = evaluator.eval(input);

    // then
    assert(result === expectedOutput)
  }

  test("should eval 'SetBinOp(setDef123, \"intersect\", setDef345)' to 'Set(3)'") {
    // given
    val input = SetBinOp(setDef123, "intersect", setDef345)
    val expectedOutput = Set(3)

    // when
    val result = evaluator.eval(input);

    // then
    assert(result === expectedOutput)
  }

  test("should eval 'SetBinOp(setDef123, \"intersect\", SetBinOp(setDef345, \"union\", setDef234))' to 'Set(2, 3)'") {
    // given
    val input = SetBinOp(setDef123, "intersect", SetBinOp(setDef345, "union", setDef234))
    val expectedOutput = Set(2, 3)

    // when
    val result = evaluator.eval(input);

    // then
    assert(result === expectedOutput)
  }

  test("should check for operation's symbols collision") {
    // given
    trait TestCollisionSetOperations {
      this: SetNotationEvaluator =>

      addOperation("union", (s1, s2) => s1 -- s2)
    }

    intercept[IllegalStateException] {
      new SetNotationEvaluator with TestSetOperations with TestCollisionSetOperations
    }
  }

}