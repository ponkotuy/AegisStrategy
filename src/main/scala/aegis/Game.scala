package aegis


/**
 *
 * @author ponkotuy
 * Date: 14/01/18
 */
case class Game(numbers: Seq[Int], trials: List[Trial] = List.empty) {
  lazy val candidate: Stream[Answer] =
    trials.foldLeft(Answer.allAnswer(numbers)) { case (xs, trial) =>
      trial.filter(xs)
    }

  def isCompleted = candidate.size == 1

  def addTrial(trial: Trial): Game = Game(numbers, trial :: trials)

  def count: Int = trials.size

  def damage: Double = trials.map(_.hitBlow.damage - 3.0).sum
}
