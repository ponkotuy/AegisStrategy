package aegis.run

import aegis.strategy._
import aegis.Answer
import aegis.Trial
import aegis.Game

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
    f(Game(Answer.allAnswers()))
  }

  def allSampling(st: Strategy): SamplingResult = {
    val result = Answer.allAnswers().map(sampling(st))
    val count = result.map(_.count).sum
    val damage = result.map(_.damage).sum
    SamplingResult(count, damage)
  }

  case class SamplingResult(count: Int, damage: Double) {
    override def toString = s"(count=$count, damage=${damage.toInt})"
  }

  def main(args: Array[String]): Unit = {
    require(Answer.allAnswers().size == 720)
    val headSt = new HeadStrategy
    val discSt = new DiscreteStrategy(headSt)
    val pointSt = new PointStrategy(new DiscretePoint)
    val newWikiSt = new PointStrategy(new NewWikiPoint)
    val randomSt = new RandomChoiceStrategy
    val results = List(
      "HeadStrategy: " -> headSt,
      "DiscreteStrategy: " -> discSt,
      "WikiStrategy + DiscreteStrategy: " -> new WikiStrategy(0 to 9)(discSt),
      "WikiStrategy + HeadStrategy: " -> new WikiStrategy(0 to 9)(headSt),
      "WikiStrategy + RandomStrategy: " -> new WikiStrategy(0 to 9)(randomSt),
      "PointStrategy + DiscretePoint: " -> pointSt,
      "PointStrategy + NewWikiPoint: " ->  newWikiSt,
      "RandomChoiceStrategy1: " -> randomSt,
      "RandomChoiceStrategy2: " -> randomSt,
      "RandomChoiceStrategy3: " -> randomSt
    ).par.map { case (title, st) => title -> allSampling(st) }
    results.foreach { case (title, result) =>
      println(title + result)
    }
  }
}
