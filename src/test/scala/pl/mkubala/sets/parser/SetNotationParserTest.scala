package pl.mkubala.sets.parser

import org.scalatest.FunSuite
import pl.mkubala.sets.SetExpression
import pl.mkubala.sets.SetDef
import pl.mkubala.sets.SetBinOp
import pl.mkubala.sets.SetBinOp
import pl.mkubala.sets.SetDef
import pl.mkubala.sets.SetBinOp
import pl.mkubala.sets.SetDef
import pl.mkubala.sets.SetBinOp
import pl.mkubala.sets.SetBinOp
import pl.mkubala.sets.SetBinOp

class SetNotationParserTest extends FunSuite {

  /* GENERAL TESTS */

  test("should return empty set definition for null input string") {
    // given
    val input = null

    // when
    val result = SetNotationParser.parse(input)

    // then
    assert(result === SetDef(Set()))
  }

  test("should return empty set definition for empty input string") {
    // given
    val input = ""

    // when
    val result = SetNotationParser.parse(input)

    // then
    assert(result === SetDef(Set()))
  }

  test("should return empty set definition for blank input string") {
    // given
    val input = "  "

    // when
    val result = SetNotationParser.parse(input)

    // then
    assert(result === SetDef(Set()))
  }

  /* SET DEFINITION TESTS */

  test("should parse '{3}'") {
    // given
    val input = "{3}"

    // when
    val result = SetNotationParser.parse(input)

    // then
    assert(result === SetDef(Set(3)))
  }

  test("should parse '3'") {
    // given
    val input = "3"

    // when
    val result = SetNotationParser.parse(input)

    // then
    assert(result === SetDef(Set(3)))
  }

  test("should parse '{1,2,3}'") {
    // given
    val input = "{1,2,3}"

    // when
    val result = SetNotationParser.parse(input)

    // then
    assert(result === SetDef(Set(1, 2, 3)))
  }

  /* BINARY OPERATIONS TESTS */

  test("should parse '{1,2,3} ^ {3,4,5}'") {
    // given
    val input = "{1,2,3} ^ {3,4,5}"

    // when
    val result = SetNotationParser.parse(input)

    // then
    assert(result === SetBinOp(SetDef(Set(1, 2, 3)), "^", SetDef(Set(3, 4, 5))))
  }

  test("should parse '{1,2,3}       ^     {3,4,5}'") {
    // given
    val input = "{1,2,3}       ^     {3,4,5}"

    // when
    val result = SetNotationParser.parse(input)

    // then
    assert(result === SetBinOp(SetDef(Set(1, 2, 3)), "^", SetDef(Set(3, 4, 5))))
  }

  test("should parse '{1,2,3} anyOperator#!? {3,4,5}'") {
    // given
    val input = "{1,2,3} anyOperator#!? {3,4,5}"

    // when
    val result = SetNotationParser.parse(input)

    // then
    assert(result === SetBinOp(SetDef(Set(1, 2, 3)), "anyOperator#!?", SetDef(Set(3, 4, 5))))
  }

  /* PRIORITY & GROUPING PARENTHESES TESTS */

  test("should parse '{1,2,3} opA {3,4,5} opB {5,6,7}'") {
    // given
    val input = "{1,2,3} opA {3,4,5} opB {5,6,7}"

    // when
    val result = SetNotationParser.parse(input)

    // then
    assert(result === SetBinOp(SetBinOp(SetDef(Set(1, 2, 3)), "opA", SetDef(Set(3, 4, 5))), "opB", SetDef(Set(5, 6, 7))))
  }

  test("should results of '{1,2,3} opA {3,4,5} opB {5,6,7}' be equals to '({1,2,3} opA {3,4,5}) opB {5,6,7}'") {
    // given
    val firstInput = "{1,2,3} opA {3,4,5} opB {5,6,7}"
    val secondInput = "({1,2,3} opA {3,4,5}) opB {5,6,7}"

    // when
    val firstResult = SetNotationParser.parse(firstInput)
    val secondResult = SetNotationParser.parse(secondInput)

    // then
    assert(firstResult === secondResult)
  }

  test("should parse '{1,2,3} opA ({3,4,5} opB {5,6,7})'") {
    // given
    val input = "{1,2,3} opA ({3,4,5} opB {5,6,7})"

    // when
    val result = SetNotationParser.parse(input)

    // then
    assert(result === SetBinOp(SetDef(Set(1, 2, 3)), "opA", SetBinOp(SetDef(Set(3, 4, 5)), "opB", SetDef(Set(5, 6, 7)))))
  }

  test("should parse '{1,2,3} opA ({3,4,5} opB ({5,6,7} opC {7,8,9}))'") {
    // given
    val input = "{1,2,3} opA ({3,4,5} opB ({5,6,7} opC {7,8,9}))"

    // when
    val result = SetNotationParser.parse(input)

    // then
    assert(result === SetBinOp(SetDef(Set(1, 2, 3)), "opA", SetBinOp(SetDef(Set(3, 4, 5)), "opB", SetBinOp(SetDef(Set(5, 6, 7)), "opC", SetDef(Set(7, 8, 9))))))
  }

  test("should parse '({1,2,3} opA {3,4,5}) opB ({5,6,7} opC {7,8,9})'") {
    // given
    val input = "({1,2,3} opA {3,4,5}) opB ({5,6,7} opC {7,8,9})"

    // when
    val result = SetNotationParser.parse(input)

    // then
    assert(result === SetBinOp(SetBinOp(SetDef(Set(1, 2, 3)), "opA", SetDef(Set(3, 4, 5))), "opB", SetBinOp(SetDef(Set(5, 6, 7)), "opC", SetDef(Set(7, 8, 9)))))
  }

  /* ERROR DETECTION TESTS */

  test("should throw exception for '{1,2,3'") {
    // given
    val input = "{1,2,3"

    // when & then
    intercept[IllegalArgumentException] {
      SetNotationParser.parse(input)
    }
  }

  test("should throw exception for '({1,2,3} op1 {3,4,5}))'") {
    // given
    val input = "({1,2,3} op1 {3,4,5}))"

    // when & then
    intercept[IllegalArgumentException] {
      SetNotationParser.parse(input)
    }
  }

}