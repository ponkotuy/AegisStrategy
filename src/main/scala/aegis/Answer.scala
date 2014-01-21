package aegis


/**
 * @author ponkotuy
 * date: 2014/01/22
 */
case class Answer(i: Int) {
  import Answer._
  val str = toStr3(i)

  def hit(y: Answer): Int = {
    f(a, y.a) + f(b, y.b) +  f(c, y.c)
  }

  def blow(y: Answer): Int = {
    f(a, y.b) + f(a, y.c) +
      f(b, y.a) + f(b, y.c) +
      f(c, y.a) + f(c, y.b)
  }

  def hitBlow(ans: Answer): HitBlow = {
    val hit = this.hit(ans)
    val blow = this.blow(ans)
    HitBlow(hit, blow)
  }

  def toSet: Set[Char] = str.toSet

  def a = str(0)
  def b = str(1)
  def c = str(2)
}

object Answer {
  def allAnswers(): Stream[Answer] = {
    var i = 12
    val builder = Stream.newBuilder[Answer]
    while(i <= 987) {
      val ans = Answer(i)
      if(ans.a != ans.b && ans.b != ans.c && ans.c != ans.a) builder += ans
      i += 1
    }
    builder.result()
  }

  def allAnswers(exc: Set[Char]): Stream[Answer] = {
    var i = 12
    val builder = Stream.newBuilder[Answer]
    while(i <= 987) {
      val ans = Answer(i)
      val str = ans.str
      if(!exc.contains(str(0)) && !exc.contains(str(1)) && !exc.contains(str(2)) &&
        str(0) != str(1) && str(1) != str(2) && str(2) != str(0)
      ) { builder += ans }
      i += 1
    }
    builder.result()
  }

  def fromChars(cs: Char*): Answer = {
    val num = cs.take(3).map(_ - '0').reduceLeft((sum, x) => sum * 10 + x)
    Answer(num)
  }

  private def toStr3(x: Int): String = {
    if(x < 100) "0" + x.toString else x.toString
//    "%03d".format(x)
  }

  private def f[T](x: T, y: T): Int = if(x == y) 1 else 0
}
