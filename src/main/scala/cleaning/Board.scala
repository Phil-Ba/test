package cleaning

/**
  *
  */
class Board(pos: String, boardArray: Seq[String]) {

  type Coordinate = (Int, Int)
  type BotMove = (Coordinate, Move)

  val botPosition: Coordinate = (pos(0).asDigit, pos(2).asDigit)

  private val dirtPositions: Seq[Coordinate] = boardArray
    .zipWithIndex
    .flatMap(line => line._1.zipWithIndex
      .withFilter(_._1 == 'd')
      .map(d => (d._2, line._2))
    )

  def isClean: Boolean = dirtPositions.isEmpty

  val canClean: Boolean = dirtPositions.contains(botPosition)

  def validMoves: Seq[BotMove] = {
    val tuples: Array[((Int, Int), Move)] = for {
      move <- Move.values
      pos = (botPosition._1 + move.x, botPosition._2 + move.y)
      if pos._1 < 5 && pos._2 < 5 && pos._1 >= 0 && pos._2 >= 0
    } yield {
      (pos, move)
    }
    tuples
  }

}
