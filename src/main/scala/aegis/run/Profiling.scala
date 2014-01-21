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
      allAnswer.foreach { case (ans, ansStr) =>
        assert(candidates(0, 0)(ans, ansStr).size == 210)
      }
    }
  }

  def candidates(hit: Int, blow: Int)(ans: Int, ansStr: String): Stream[(Int, String)] = {
    allAnswer.filter { case (num, str) =>
      val h = this.hit(str, ansStr)
      val b = this.blow(str, ansStr)
      hit == h && blow == b
    }
  }

  @inline def toStr3(x: Int): String = "%03d".format(x)

  val allAnswer: Stream[(Int, String)] =
    (12 to 987).toStream.map(x => x -> toStr3(x))
      .filter { case (num, str) => str(0) != str(1) && str(1) != str(2) && str(2) != str(0) }

//  @inline def hit(xs: String, ys: String): Int =
//    xs.zip(ys).count { case (x, y) => x == y }

  def hit(xs: String, ys: String): Int =
    f(xs(0), ys(0)) + f(xs(1), ys(1)) +  f(xs(2), ys(2))

  def blow(xs: String, ys: String): Int = {
    f(xs(0), ys(1)) + f(xs(0), ys(2)) +
      f(xs(1), ys(0)) + f(xs(1), ys(2)) +
      f(xs(2), ys(0)) + f(xs(2), ys(1))
  }

  def f[T](x: T, y: T): Int = if(x == y) 1 else 0

  def printProfile(title: String)(f: => Unit): Unit = {
    println(s"$title time: ${profile(f)/1000000.0}ms")
  }

  def profile(f: => Unit): Long = {
    val t0 = System.nanoTime()
    f
    System.nanoTime() - t0
  }
}
