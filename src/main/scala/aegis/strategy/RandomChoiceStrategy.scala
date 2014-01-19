package aegis.strategy

import aegis.{Answer, Game}
import scala.util.Random

/**
 *
 * @author ponkotuy
 * Date: 14/01/19
 */
class RandomChoiceStrategy extends Strategy {
  val random = new Random
  def choice(game: Game): Answer =
    game.candidate.minBy(_ => random.nextDouble())
}
