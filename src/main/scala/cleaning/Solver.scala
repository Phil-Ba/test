package cleaning

import scala.annotation.tailrec

class Solver {

  def solve(botStart: String, board: Seq[String]) = {
    val staringBoard = Board(botStart, board)
    solveRec(Stream((staringBoard, Seq.empty)), Seq.empty)
      .filter(_._1.isClean)
      .filter(_._2.head == Move.Clean)
      .take(1)
      .toList
  }

  @tailrec
  private def solveRec(boardsAndMoves: Stream[(Board, Seq[Move])],
                       boardHistory: Seq[Board]): Stream[(Board, Seq[Move])] = {
    //    val newBoardsAndMoves = streamOfMoves(boardsAndMoves, boardHistory)
    //    val newBoardHistory = boardHistory ++ newBoardsAndMoves.map(_._1)

    val stream: Seq[(Board, Seq[Move])] = for {
      currentBoardsAndMoves <- boardsAndMoves
      newBoardsAndMoves <- streamOfMoves(currentBoardsAndMoves, boardHistory)
    } yield {
      newBoardsAndMoves
    }
    val newBoardHistory = boardHistory ++ stream.map(_._1)

    //    boardsAndMoves.append(solveRec(stream, newBoardHistory))
    //    solveRec(boardsAndMoves.append(stream), newBoardHistory)
    solveRec(boardsAndMoves ++ stream, newBoardHistory)
  }

  private def streamOfMoves(currentBoardAndMoves: (Board, Seq[Move]), boardHistory: Seq[Board]) = {
    val (currentBoard, moves) = currentBoardAndMoves
    if (currentBoard.isClean) {
      Seq.empty
    }

    val newMoves = doAllPossibleMoves(currentBoard)

    val newBoardsAndMoves = newMoves.withFilter({ case (board, _) => boardHistory.contains(board) == false })
      .map({ case (board, move) => (board, move +: moves) })

    newBoardsAndMoves
  }

  private def doAllPossibleMoves(currentBoard: Board): Seq[(Board, Move)] = {
    val validMoves = currentBoard.validMoves
    validMoves.map(move => (currentBoard.moveBot(move), move._2))
  }


}


