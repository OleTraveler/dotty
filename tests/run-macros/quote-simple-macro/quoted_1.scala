import scala.quoted._

object Macros {
  inline def foo(inline i: Int, dummy: Int, j: Int): Int = ${ bar('i, 'j) }
  def bar(x: Expr[Int], y: Expr[Int]) (using QuoteContext): Expr[Int] = '{ ${Expr(x.unliftOrError)} + $y }
}
