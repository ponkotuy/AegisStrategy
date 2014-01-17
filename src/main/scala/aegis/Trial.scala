package aegis

/**
 *
 * @author ponkotuy
 * Date 14/01/17
 */
case class Trial(answer: Answer, hitBlow: HitBlow) {
  def filter(cands: Seq[Answer]): Seq[Answer] =
    cands.filter(_.hitBlow(answer) == hitBlow)
}
