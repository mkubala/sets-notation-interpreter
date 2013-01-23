package pl.mkubala.sets

sealed trait SetNotationElement
case class SetSimpleElement(elements: Set[Int]) extends SetNotationElement
case class SetBinaryOpElement(opSymbol: String, leftBranch: SetNotationElement, rightBranch: SetNotationElement) extends SetNotationElement
