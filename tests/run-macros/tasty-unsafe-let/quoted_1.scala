import scala.quoted._

object Macros {

  inline def let[T](rhs: => T)(inline body: T => Unit): Unit =
    ${ impl('rhs, 'body) }

  private def impl[T: Type](rhs: Expr[T], body: Expr[T => Unit])(using qctx: QuoteContext) : Expr[Unit] = {
    import qctx.reflect._

    val rhsTerm = rhs.unseal

    import qctx.reflect.{let => letTerm}
    letTerm(rhsTerm) { rhsId =>
      Expr.betaReduce('{$body(${rhsId.seal.asInstanceOf[Expr[T]]})}).unseal // Dangerous uncheked cast!
    }.seal.cast[Unit]
  }


}
