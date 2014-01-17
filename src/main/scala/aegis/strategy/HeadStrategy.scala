package aegis.strategy

import aegis.{Answer, Game}

/**
 *
 * @author ponkotuy
 * Date: 14/01/18
 */
class HeadStrategy extends Strategy {
  def choice(game: Game): Answer =
    game.candidate.head
}
