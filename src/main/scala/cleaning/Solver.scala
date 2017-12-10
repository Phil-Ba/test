package cleaning

import scala.annotation.tailrec
import scala.collection.mutable

class Solver {

  type boardWithMoves = (Board, Seq[Move])

  def solve(botStart: String, board: Seq[String]) = {
    val staringBoard = Board(botStart, board)
    solveRec(Stream.empty, mutable.Queue((staringBoard, Seq.empty)), Seq.empty)
      .filter(x => x._1.isClean && x._2.head == Move.Clean)
      //            .filter(_._1.isClean)
      //      .filter(_._2.head == Move.Clean)
      .take(5)
      .toList
  }

  @tailrec
  private def solveRec(boardsAndMoves: Stream[boardWithMoves], workStack: mutable.Queue[boardWithMoves],
                       boardHistory: Seq[Board]): Stream[(Board, Seq[Move])] = {
    //    val newBoardsAndMoves = streamOfMoves(boardsAndMoves, boardHistory)
    //    val newBoardHistory = boardHistory ++ newBoardsAndMoves.map(_._1)
    if (workStack.isEmpty) {
      println("finished?!")
      return boardsAndMoves
    }
    println(workStack.size)

    val currentBoardsAndMoves = workStack.dequeue()

    val stream: Seq[(Board, Seq[Move])] = for {
      //      currentBoardsAndMoves <- workStack.dequeue()
      newBoardsAndMoves <- streamOfMoves(currentBoardsAndMoves, boardHistory)
    } yield {
      newBoardsAndMoves
    }
    val newBoardHistory = boardHistory ++ stream.map(_._1)

    //    boardsAndMoves.append(solveRec(stream, newBoardHistory))
    //    solveRec(boardsAndMoves.append(stream), newBoardHistory)
    workStack.enqueue(stream: _*)
    solveRec(boardsAndMoves ++ (stream), workStack, newBoardHistory)
  }

  private def streamOfMoves(currentBoardAndMoves: boardWithMoves, boardHistory: Seq[Board]) = {
    val (currentBoard, moves) = currentBoardAndMoves
    if (currentBoard.isClean) {
      Seq.empty
    }

    val newMoves = doAllPossibleMoves(currentBoard)

    val newBoardsAndMoves = newMoves
      .withFilter({ case (board, _) => boardHistory.contains(board) == false })
      .map({ case (board, move) => (board, move +: moves) })

    newBoardsAndMoves
  }

  private def doAllPossibleMoves(currentBoard: Board): Seq[(Board, Move)] = {
    val validMoves = currentBoard.validMoves
    validMoves.map(move => (currentBoard.moveBot(move), move._2))
  }


}


