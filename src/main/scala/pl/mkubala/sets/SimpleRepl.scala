package pl.mkubala.sets

import scala.annotation.tailrec

import pl.mkubala.sets.evaluator.BasicSetOperations
import pl.mkubala.sets.evaluator.SetNotationEvaluator
import pl.mkubala.sets.parser.SetNotationParser

object SimpleRepl extends App {

  val evaluator = new SetNotationEvaluator with BasicSetOperations

  @tailrec
  def readInput() {
    print("> ");
    readLine() match {
      case "quit" | "exit" | "bye" => println("bye!")
      case expr => {
        try {
          println("= " + evaluator.eval(SetNotationParser.parse(expr)))
        } catch {
          case ex: Exception => println("Error: " + ex.getMessage)
        }
        readInput
      }
    }
  }

  println("=== Sets Notation Interpreter - interactive shell ===")
  println("Usage: Type expression which you want to parse and press Enter to see results")
  println("If you want to exit, use one of the following expressions:\n 'bye', 'quit' or 'exit'")
  println("Have fun!")
  readInput
}
