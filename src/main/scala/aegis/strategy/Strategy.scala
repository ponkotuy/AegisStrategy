package aegis.strategy

import aegis.{Game, Answer, Trial}

/**
 *
 * @author ponkotuy
 * Date: 14/01/17
 */
trait Strategy {
  def choice(game: Game): Answer
}
