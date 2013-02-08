package pl.mkubala.sets

import pl.mkubala.sets.evaluator.BasicSetOperations
import pl.mkubala.sets.evaluator.SetNotationEvaluator
import pl.mkubala.sets.parser.SetNotationParser
import scala.annotation.tailrec

object SimpleRepl extends App {

  val evaluator = new SetNotationEvaluator with BasicSetOperations

  @tailrec
  def readInput() {
    print("> ");
    readLine() match {
      case "quit" | "exit" | "bye" =>
      case expr => {
        try {
          println("= " + evaluator.eval(SetNotationParser.parse(expr)))
        } catch {
          case ex: Exception => println("Error: " + ex)
        }
        readInput
      }
    }
  }

  readInput

}