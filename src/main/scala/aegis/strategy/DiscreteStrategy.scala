package aegis.strategy

import aegis.{Game, Answer}

/**
 *
 * @author ponkotuy
 * Date: 14/01/17
 */
class DiscreteStrategy(st: Strategy) extends Strategy {
  def choice(game: Game): Answer = {
    val choiced: Set[Char] = game.trials.flatMap(_.answer.toSet).toSet
    val candNumbers: Set[Char] = game.candidate.flatMap(_.toSet).toSet
    val nonChoiced = candNumbers -- choiced
    nonChoiced.size match {
      case num if 3 <= num => Answer.fromChars(nonChoiced.toSeq:_*)
      case 0 => st.choice(game)
      case _ =>
        val xs = nonChoiced.toSeq ++ choiced.toSeq
        Answer.fromChars(xs:_*)
    }
  }
}
