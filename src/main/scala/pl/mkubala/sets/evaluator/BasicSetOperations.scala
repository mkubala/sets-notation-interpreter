package pl.mkubala.sets.evaluator

trait BasicSetOperations {
  this: SetNotationEvaluator =>

  val intersect: SetOperation = (s1, s2) => s1 intersect s2
  val union: SetOperation = (s1, s2) => s1 union s2
  val difference: SetOperation = (s1, s2) => s1 -- s2
  val symmetricDifference: SetOperation = (s1, s2) => union(difference(s1, s2), difference(s2, s1))

  addOperation("^", intersect)
  addOperation("v", union)
  addOperation("""\""", difference)
  addOperation("-", symmetricDifference)

}

