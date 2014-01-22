package aegis


/**
 *
 * @author ponkotuy
 * Date: 14/01/18
 */
case class Game(candidate: Stream[Answer], trials: List[Trial] = List.empty) {
  def isCompleted = candidate.size == 1

  def addTrial(trial: Trial): Game =
    Game(trial.filter(candidate), trial::trials)

  def count: Int = trials.size

  def damage: Double = trials.map(_.hitBlow.damage - 3.0).sum
}
