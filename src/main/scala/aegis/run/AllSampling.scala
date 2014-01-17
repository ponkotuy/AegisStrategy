package aegis.run

import aegis.strategy.{WikiStrategy, DiscreteStrategy, HeadStrategy, Strategy}
import aegis.{Trial, Game, Answer}

/**
 *
 * @author ponkotuy
 * Date: 14/01/18.
 */
object AllSampling {
  val Numbers = 0 to 9

  def sampling(st: Strategy)(ans: Answer): Game = {
    def f(game: Game): Game = {
      if(game.isCompleted) return game
      val choice = st.choice(game)
      val trial = Trial(choice, ans.hitBlow(choice))
      f(game.addTrial(trial))
    }
    f(Game(Numbers))
  }

  def allSampling(st: Strategy): SamplingResult = {
    val result = Answer.allAnswer(Numbers).map(sampling(st))
    val count = result.map(_.count).sum
    val damage = result.map(_.damage).sum
    SamplingResult(count, damage)
  }

  case class SamplingResult(count: Int, damage: Double) {
    override def toString() = (count, damage.toInt).toString()
  }

  def main(args: Array[String]): Unit = {
    val headSt = new HeadStrategy
    val discSt = new DiscreteStrategy(headSt)
    println(s"HeadStrategy: ${allSampling(headSt)}")
    println(s"DiscreteStrategy: ${allSampling(discSt)}")
    println(s"WikiStrategy + DiscreteStrategy: ${allSampling(new WikiStrategy(discSt))}")
    println(s"WikiStrategy + HeadStrategy: ${allSampling(new WikiStrategy(headSt))}")
  }
}
