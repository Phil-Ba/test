import java.nio.file.{Files, Paths}
import java.time.{Instant, Duration => jDur}
import java.util.concurrent.TimeUnit

import cleaning.Solver
import com.typesafe.scalalogging.StrictLogging
import org.pmw.tinylog.Configurator

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

/**
  *
  */
object Main extends StrictLogging {
  def main(args: Array[String]): Unit = {
    Configurator
      .defaultConfig()
      .formatPattern("{level} {date:HH:mm:ss} [{thread}] {class}: {message}")
      .writingThread(true)
      .activate()

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
      val dur = jDur.between(start, end)
      logger.info("Total time: {}", dur)
      solution.map(x => (s"moves(${x._2.size}) = ${x._2.reverse}"))
    }.map { x =>
      logger.info(x.mkString("\r\n"))
      Files.write(Paths.get("lines.txt"), x.asJava)
    }

    Await.result(task, Duration(2, TimeUnit.HOURS))
  }
}
