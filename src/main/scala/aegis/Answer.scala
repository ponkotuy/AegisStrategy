package aegis


/**
 * @author ponkotuy
 * date: 2013/12/31.
 */
class Answer(val a: Int, val b: Int, val c: Int) {
  import Answer._

  def check(hitBlow: HitBlow, cand: Seq[Answer] = allAnswer()): Seq[Answer] =
    cand.filter(_.hitBlow(this) == hitBlow)

  def hitBlow(other: Answer): HitBlow = {
    val hit = List(a == other.a, b == other.b, c == other.c).count(identity)
    val blow = (this.toSet & other.toSet).size - hit
    HitBlow(hit, blow)
  }

  def toSet: Set[Int] = Set(a, b, c)

  override def equals(other: Any): Boolean = other match {
    case Answer(a_, b_, c_) => a == a_ && b == b_ && c == c_
    case _ => false
  }

  override def hashCode(): Int =
    a.hashCode() + b.hashCode() + c.hashCode()

  override def toString() = s"Answer($a, $b, $c)"
}

object Answer {
  def apply(a: Int, b: Int, c: Int): Answer = {
    require(a < 10 && b < 10 && c < 10, "Input numbers must not be under 10.")
    require(a != b && b != c && c != a, "Input must have different numbers.")
    new Answer(a, b, c)
  }

  def apply(xs: Seq[Int]): Answer = {
    require(3 <= xs.size)
    apply(xs(0), xs(1), xs(2))
  }

  def unapply(ans: Answer): Option[(Int, Int, Int)] =
    Some((ans.a, ans.b, ans.c))

  def allAnswer(numbers: Seq[Int] = 0 to 9): Seq[Answer] = {
    numbers.foreach(n => require(0 <= n && n <= 9))
    for {
      a <- numbers
      b <- numbers
      if a != b
      c <- numbers
      if a != c && b != c
    } yield new Answer(a, b, c)
  }
}
