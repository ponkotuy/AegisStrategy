package aegis.strategy

import aegis.{Game, Answer}

/**
 *
 * @author ponkotuy
 * Date: 14/01/17
 */
class DiscreteStrategy(st: Strategy) extends Strategy {
  def choice(game: Game): Answer = {
    val choiced: Set[Int] = game.trials.flatMap(_.answer.toSet).toSet
    val candNumbers: Set[Int] = game.candidate.flatMap(_.toSet).toSet
    val nonChoiced = candNumbers -- choiced
    nonChoiced.size match {
      case num if 3 <= num => Answer(nonChoiced.toSeq)
      case 0 => st.choice(game)
      case _ =>
        val xs = nonChoiced.toSeq ++ choiced.toSeq
        Answer(xs)
    }
  }
}
