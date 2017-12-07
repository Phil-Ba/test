package cleaning

class Solver {

  def solve(botStart: String, board: Seq[String]) = {
    val staringBoard = Board(botStart, staringBoard)

  }


  def doAllPossibleMoves(currentBoard: Board, movesHistory: Seq[Move], boardHistory: Seq[Board]): Seq[(Board, Move)] = {
    val validMoves = currentBoard.validMoves
    validMoves.map(move => (currentBoard.moveBot(move), move._2))
  }


}


