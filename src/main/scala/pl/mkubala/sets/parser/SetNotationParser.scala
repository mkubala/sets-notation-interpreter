package pl.mkubala.sets.parser

import scala.util.parsing.combinator.JavaTokenParsers
import pl.mkubala.sets.SetExpression
import pl.mkubala.sets.SetBinOp
import pl.mkubala.sets.SetDef

object SetNotationParser extends JavaTokenParsers {

  def expr: Parser[SetExpression] = (set | subExpr) ~ rep(op ~ (set | subExpr)) ^^ {
    case first ~ Nil => first
    case first ~ rest => (rest foldLeft (first))((left, right) =>
      right match {
        case op ~ next => SetBinOp(left, op, next)
      })
  }

  def subExpr: Parser[SetExpression] = "(" ~> expr <~ ")"

  def op: Parser[String] = """[!"#$%&'*+./:;<=>?@\^_`|~\-\\A-Za-z]+""".r

  def set: Parser[SetExpression] = setMultiElem | setSingleElem

  def setSingleElem: Parser[SetExpression] = wholeNumber ^^ { num => SetDef(Set(num.toInt)) }

  def setMultiElem: Parser[SetExpression] = "{" ~> repsep(wholeNumber, ',') <~ "}" ^^
    { setElements => SetDef(Set() ++ setElements map (_.toInt)) }

  def parse(literalSetExpression: String): SetExpression =
    if (literalSetExpression == null || (literalSetExpression.trim isEmpty)) {
      SetDef(Set())
    } else {
      parseAll(expr, literalSetExpression) match {
        case Success(res, _) => res
        case NoSuccess(msg, _) => throw new IllegalArgumentException(msg)
      }
    }

}