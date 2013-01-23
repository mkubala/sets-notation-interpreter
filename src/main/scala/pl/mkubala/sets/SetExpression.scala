package pl.mkubala.sets

sealed trait SetExpression
case class SetDef(elements: Set[Int]) extends SetExpression
case class SetBinOp(opSymbol: String, leftBranch: SetExpression, rightBranch: SetExpression) extends SetExpression
