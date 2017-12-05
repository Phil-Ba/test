import java.util.Scanner

import scala.collection.mutable
import scala.io.StdIn

val sc = new Scanner(System.in)
val entries = sc.nextInt()
val map = new mutable.HashMap[String, Int]()

for (_ <- 1 to entries) {
  val inp: Array[String] = sc.nextLine().split(" ")
  map += inp.head -> inp.last.toInt
}
println(map)
while (sc.hasNext){
  val s = sc.next()
  println(map.get(s)
    .fold("Not found")(i => s + "=" + i))
}



//def listMethod(l: List[Int]) = l.sum
//val l2 = List(2, 5, 7, 4, 1, 8)
//val l = List(1, 2, 3, 4, 5)
//l(2)
//l.slice(0,2)
//l.slice(2+1,l.length)
//l.zipWithIndex.map(idx => l.drop(idx._2))
//l.scanLeft(l)((l, _) => l.tail)
//
//Solution.solve(List(2, 5, 7, 4, 1, 8))
//val l = List(2,5,1,4,9,3)
//val root = Solution.buildTree(l)
//root.min(0,1)
//root.min(1,2)
//root.min(1,3)
//root.min(3,4)
//            0   1   2   3   4   5   6   7   8   9
//val l2 = List(10, 20, 30, 40, 11, 22, 33, 44, 15, 5)
//            0  1  2  3  4  5
val l3 = List(2, 5, 1, 4, 9, 3)
//val root2 = Solution.buildTree(l2)
val root3 = Solution.buildTree(l3)
//root2.min(0,1)
//root2.min(1,2)
//root3.min(4, 5)
//root3.min(1, 4)
//root3.min(1,3)
root3.min(1, 1)
//root3.min(0, 1)
//root3.min(3, 5)
//root3.min(3, 4)