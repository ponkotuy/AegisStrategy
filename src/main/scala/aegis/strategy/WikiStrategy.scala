package aegis.strategy

import aegis.{HitBlow, Answer, Game}

/**
 * この通りにやってみた
 * [[http://senkosinki.gamerch.com/%E6%95%B0%E5%AD%97%E5%BD%93%E3%81%A6%E3%81%AE%E3%82%B3%E3%83%84]]
 *
 * @author ponkotuy
 * Date: 14/01/18
 */
class WikiStrategy(st: Strategy) extends Strategy {
  import WikiStrategy._
  def choice(game: Game): Answer = {
    implicit val gameImpl = game
    if(game.trials.size == 1) {
      val trial = game.trials.head
      val ans = trial.answer
      trial.hitBlow match {
        case HitBlow(0, 0) => Answer(rest.toSeq)
        case HitBlow(0, 1) => Answer(ans.b, ans.a, rest.head)
        case HitBlow(0, 2) => Answer(ans.b, ans.c, rest.head)
        case HitBlow(0, 3) => Answer(ans.b, ans.c, ans.a)
        case HitBlow(1, 0) => Answer(ans.a, ans.c, rest.head)
        case HitBlow(1, 1) =>
          val xs = rest.toSeq
          Answer(ans.a, xs(0), xs(1))
        case HitBlow(1, 2) => Answer(ans.a, ans.c, ans.b)
        case HitBlow(2, 0) => Answer(ans.a, ans.c, rest.head)
      }
    } else {
      st.choice(game)
    }
  }
}

object WikiStrategy {
  def rest(implicit game: Game): Set[Int] =
    game.numbers.toSet -- game.trials.flatMap(_.answer.toSet)
}