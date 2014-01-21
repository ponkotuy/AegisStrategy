package aegis.run

import aegis.{HitBlow, Trial, Game, Answer}

/**
 *
 * @author ponkotuy
 * Date: 14/01/21
 */
object Profiling {
  def main(args: Array[String]) {
    printProfile("Old") {
      Answer.allAnswer().foreach { ans =>
        assert(Game(0 to 9, List(Trial(ans, HitBlow(0, 0)))).candidate.size == 210)
      }
    }
    printProfile("New") {
      allAnswer.foreach { ans =>
        assert(candidates(0, 0)(ans).size == 210)
      }
    }
  }

  def candidates(hit: Int, blow: Int)(ans: Ans): Stream[Ans] = {
    allAnswer.filter { x =>
      hit == x.hit(ans) && blow == x.blow(ans)
    }
  }

  val allAnswer: Stream[Ans] = {
    var i = 12
    val builder = Stream.newBuilder[Ans]
    while(i <= 987) {
      val ans = Ans(i)
      val str = ans.str
      if(str(0) != str(1) && str(1) != str(2) && str(2) != str(0)) builder += ans
      i += 1
    }
    builder.result()
  }

  def f[T](x: T, y: T): Int = if(x == y) 1 else 0

  case class Ans(i: Int) {
    val str = toStr3(i)

    def hit(other: Ans): Int = {
      val xs = this.str
      val ys = other.str
      f(xs(0), ys(0)) + f(xs(1), ys(1)) +  f(xs(2), ys(2))
    }

    def blow(other: Ans): Int = {
      val xs = this.str
      val ys = other.str
      f(xs(0), ys(1)) + f(xs(0), ys(2)) +
        f(xs(1), ys(0)) + f(xs(1), ys(2)) +
        f(xs(2), ys(0)) + f(xs(2), ys(1))
    }

    private[this] def toStr3(x: Int): String = "%03d".format(x)
  }

  def printProfile(title: String)(f: => Unit): Unit = {
    println(s"$title time: ${profile(f)/1000000.0}ms")
  }

  def profile(f: => Unit): Long = {
    val t0 = System.nanoTime()
    f
    System.nanoTime() - t0
  }
}
