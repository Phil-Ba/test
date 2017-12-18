package cleaning

import cleaning.Board.{BotMove, Coordinate}

/**
  *
  */
class Board private(private val botPosition: Coordinate, private val dirtPositions: Seq[Coordinate]) {

  def isClean: Boolean = dirtPositions.isEmpty

  val canClean: Boolean = dirtPositions.contains(botPosition)

  def clean(): Board = new Board(botPosition, dirtPositions.diff(Seq(botPosition)))

  def moveBot(botMove: BotMove): Board = if (botMove._2 == Move.Clean) clean() else new Board(botMove._1, dirtPositions)

  def validMoves: Seq[BotMove] = {
    if (canClean) {
      Seq((botPosition, Move.Clean))
    } else {
    val tuples: Array[((Int, Int), Move)] = for {
      move <- Move.movingMoves
      pos = (botPosition._1 + move.x, botPosition._2 + move.y)
      if pos._1 < 5 && pos._2 < 5 && pos._1 >= 0 && pos._2 >= 0
    } yield {
      (pos, move)
    }
    tuples
    }
  }


  def canEqual(other: Any): Boolean = other.isInstanceOf[Board]

  override def equals(other: Any): Boolean = other match {
    case that: Board =>
      (that canEqual this) &&
        botPosition == that.botPosition &&
        dirtPositions == that.dirtPositions
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(botPosition, dirtPositions)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }


  override def toString = s"Board($botPosition, $dirtPositions)"
}

object Board {
  type Coordinate = (Int, Int)
  type BotMove = (Coordinate, Move)

  def apply(pos: String, boardArray: Seq[String]): Board = {
    val botPosition: Coordinate = (pos(0).asDigit, pos(2).asDigit)

    val dirtPositions: Seq[Coordinate] = boardArray
      .zipWithIndex
      .flatMap(line => line._1.zipWithIndex
        .withFilter(_._1 == 'd')
        .map(d => (d._2, line._2))
      )
    new Board(botPosition, dirtPositions)
  }
}
