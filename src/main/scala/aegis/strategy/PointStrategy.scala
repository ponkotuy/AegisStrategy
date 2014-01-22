package aegis.strategy

import aegis.{HitBlow, Trial, Answer, Game}
import scala.util.Random

/**
 * 候補の中から最もPointが高いものを選ぶ。
 *
 * @author ponkotuy
 * Date: 14/01/18.
 */
class PointStrategy(point: Point) extends Strategy {
  def choice(game: Game): Answer = {
    game.candidate.maxBy(point.calc(game))
  }
}

trait Point {
  def calc(game: Game)(ans: Answer): Double
}

class DiscretePoint extends Point {
  def calc(game: Game)(ans: Answer): Double = {
    val counts = game.trials
      .flatMap(_.answer.str)
      .groupBy(identity)
      .mapValues(_.size)
      .withDefaultValue(0)
    ans.str.map(counts).sum
  }
}

class RandomPoint extends Point {
  val random = new Random
  def calc(game: Game)(ans: Answer): Double =
    random.nextDouble()
}

class NewWikiPoint extends Point {
  import NewWikiPoint._
  def calc(game: Game)(ans: Answer): Double = {
    hitBlows.par.map { hitblow =>
      val candCount = game.addTrial(Trial(ans, hitblow)).candidate.size
      candCount*candCount
    }.sum
  }
}

object NewWikiPoint {
  val hitBlows: Seq[HitBlow] = for {
    hit <- 0 to 3
    blow <- 0 to 3
    if (hit + blow) <= 3
    if !(hit == 2 && blow == 1)
  } yield HitBlow(hit, blow)
}