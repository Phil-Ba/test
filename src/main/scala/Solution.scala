import scala.io.StdIn

/**
  *
  */
object Solution {
  def main(args: Array[String]): Unit = {
    val cases = StdIn.readLine().trim.split(" ").map(_.toInt).last
    val values = StdIn.readLine().trim
      .split(" ")
      //      .par
      .map(_.toInt)
    //      .seq
    val tree = buildTree(values)

    val inputs = (1 to cases)
      .map(_ => StdIn.readLine().split(" ").map(_.toInt))
      .foreach(idxs => println(tree.min(idxs.head, idxs.last)))
    //    inputs
    //   <   .foreach(idxs => solve(idxs.head, idxs.last, values))
  }

  def solve(startIdx: Int, endIdx: Int, vals: Seq[Int]) = {
    val min = vals.view(startIdx, endIdx + 1)
      .par
      .min
    println(min)
  }

  def buildTree(vals: Seq[Int]) = {
    buildNode(vals, 0, vals.size - 1)
  }

  def buildNode(vals: Seq[Int], s: Int, e: Int): Node = {
    if (s == e) {
      Leaf(s, s, vals(s))
    } else {
      val mid = (s + e) / 2
      //      println(s"current $s-$e")
      //      println(s"next left($s, $mid), right(${mid + 1}, $e")
      SNode(s, e, buildNode(vals, s, mid), buildNode(vals, mid + 1, e))
    }

  }

  abstract class Node() {
    val value: Int
    val s: Int
    val e: Int

    def min(s: Int, e: Int): Int
  }

  case class SNode(s: Int, e: Int, l: Node, r: Node) extends Node {
    val value = math.min(l.value, r.value)

    override def min(s: Int, e: Int): Int = {
      //      println(s"(${this.s},${this.e})")
      if (s == this.s && this.e <= e) {
        println(s"found $value for $s,$e")
        value
      }
      //contains whole subrange
      else if (s >= this.s && e <= this.e) {
        val mid = (s + e) / 2
        val minL = math.min(l.e, mid)
        val minR = math.min(r.s, mid + 1)
        println(s"(${this.s},${this.e}) $s,$e => trying left($s,${mid}) right(${mid + 1},$e)}")
        //                println(s"$s,$e => trying left($s,${e}) right(${s},$e)}")
        //        val i = math.min(l.min(s, e), r.min(s, e))
        val i = math.min(l.min(s, minL), r.min(minR, e))
        //        val i = math.min(l.min(s, mid), r.min(mid + 1, e))
        //        println(s"min of $s,$e = $i")
        i
      }
      //      else if (e <= this.e || s >= this.s) {
      //        println(s"min of $s,$e = $i")
      //        println(s"??? (${this.s},${this.e}) $s,$e ")
      //        math.min(l.min(s, e), r.min(s, e))
      //        r.min(s, e)
      //        contains start but not end
      // else if (s >= this.s && e > this.e) {
      //      }
      //        l.min(s, e)
      //      }
      else {
        println("bounce")
        Int.MaxValue
      }
    }
  }

  case class Leaf(s: Int, e: Int, value: Int) extends Node {
    override def min(s: Int, e: Int): Int = if (s == this.s && e == this.e) {
      println(s"Leaf(${this.s},${this.e}) $s,$e =>$value")
      value
    } else {
      Int.MaxValue
    }
  }

}