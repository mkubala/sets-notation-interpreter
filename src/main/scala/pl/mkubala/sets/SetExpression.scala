package pl.mkubala.sets

sealed trait SetExpression
case class SetDef(elements: Set[Int]) extends SetExpression
case class SetBinOp(leftBranch: SetExpression, opSymbol: String, rightBranch: SetExpression) extends SetExpression
