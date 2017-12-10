import java.nio.file.{Files, Paths}
import java.time.{Instant, Duration => jDur}
import java.util.concurrent.TimeUnit

import cleaning.Solver

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}


val start = Instant.now()

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits._

val task = Future {
  val solution = new Solver().solve("0,0", Array(
    "----d",
    "----d",
    "----d",
    "----d",
    "----d")
  )
  val end = Instant.now()
  jDur.between(start, end)
  solution.map(x => (s"moves(${x._2.size}) = ${x._2.reverse}"))
}.map { x =>
  Files.write(Paths.get("lines.txt"), x.asJava)
}

Await.result(task, Duration(2, TimeUnit.MINUTES))