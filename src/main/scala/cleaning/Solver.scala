package cleaning

import java.time.{Duration, Instant}

import scala.annotation.tailrec
import scala.collection.mutable

class Solver {

  type boardWithMoves = (Board, Seq[Move])

  def solve(botStart: String, board: Seq[String]) = {
    val start = Instant.now()
    val staringBoard = Board(botStart, board)
    //    val res = solveRec(Stream.empty, mutable.Queue((staringBoard, Seq.empty)), Seq.empty)
    val equalses: Stream[(Board, Seq[Move])] = Stream.cons((staringBoard, Seq.empty), solveRec(mutable.Queue((staringBoard, Seq.empty)), Seq.empty))
    val res = equalses
      .withFilter(x => x._1.isClean && x._2.head == Move.Clean)
      //            .filter(_._1.isClean)
      //      .filter(_._2.head == Move.Clean)
      .map(x => {
      println(x);
      x
    })
      .take(1)
      .toList
    val end = Instant.now()
    println(Duration.between(start, end))
    println(res.size)
    res
  }

  @tailrec
  private def solveRec(workStack: mutable.Queue[boardWithMoves], boardHistory: Seq[Board]): Stream[(Board, Seq[Move])] = {
    //    val newBoardsAndMoves = streamOfMoves(boardsAndMoves, boardHistory)
    //    val newBoardHistory = boardHistory ++ newBoardsAndMoves.map(_._1)
    if (workStack.isEmpty) {
      println("finished?!")
      return Stream.empty
    }
    //    println(workStack.size)

    //take current work item from queue
    val currentBoardsAndMoves = workStack.dequeue()
    val newBoardHistory = currentBoardsAndMoves._1 +: boardHistory

    val newBoardsAndMoves: Seq[(Board, Seq[Move])] = for {
      //      currentBoardsAndMoves <- workStack.dequeue()
      //create all new moves for a board and given history
      newBoardsAndMoves <- streamOfMoves(currentBoardsAndMoves, newBoardHistory)
    } yield {
      newBoardsAndMoves
    }

    //    boardsAndMoves.append(solveRec(newBoardsAndMoves, newBoardHistory))
    //    solveRec(boardsAndMoves.append(newBoardsAndMoves), newBoardHistory)
    workStack.enqueue(newBoardsAndMoves: _*)
    //    val moveses = newBoardsAndMoves.foldLeft(boardsAndMoves)({ case (acc, cur) => acc :+ cur })
    //    solveRec(moveses, workStack, newBoardHistory)
    solveRec(workStack, newBoardHistory)
    //            solveRec(boardsAndMoves #::: newBoardsAndMoves, workStack, newBoardHistory)
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


