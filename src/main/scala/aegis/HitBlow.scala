package aegis

/**
 * @author ponkotuy
 * date: 2013/12/31
 */
case class HitBlow(hit: Int, blow: Int) {
  def damage: Double =
    (hit, blow) match {
      case (0, 0) => 1.0
      case (0, 1) => 1.2
      case (0, 2) => 1.6
      case (0, 3) => 2.3
      case (1, 0) => 1.3
      case (1, 1) => 1.8
      case (1, 2) => 2.5
      case (2, 0) => 2.0
      case (3, 0) => 3.0
    }
}
