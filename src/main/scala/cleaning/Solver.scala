package cleaning

import com.typesafe.scalalogging.StrictLogging

import scala.collection.mutable

class Solver extends StrictLogging {

  type boardWithMoves = (Board, Seq[Move])

  def solve(botStart: String, board: Seq[String]) = {
    val staringBoard = Board(botStart, board)
    solveRec(mutable.Queue((staringBoard, Seq.empty)), Seq(staringBoard))
      //      .filter(x => x._1.isClean && x._2.head == Move.Clean)
      .filter(b => {
      //      println("testing: " + b._1)
      b._1.isClean && b._2.head == Move.Clean
    })
      .map(x => {
        logger.info("found one: {}", x)
        x
      })
      //                  .filter(_._1.isClean)
      //      .filter(_._2.head == Move.Clean)
      .take(50)
      .toList
  }

  //  @tailrec
  private def solveRec(workStack: mutable.Queue[boardWithMoves],
                       boardHistory: Seq[Board]): Stream[(Board, Seq[Move])] = {
    //    val newBoardsAndMoves = streamOfMoves(boardsAndMoves, boardHistory)
    //    val newBoardHistory = boardHistory ++ newBoardsAndMoves.map(_._1)
    if (workStack.isEmpty) {
      println("finished?!")
      return Stream.empty
    }
    //    println(workStack.size)

    val currentBoardsAndMoves = workStack.dequeue()
    //    println("cur: " + currentBoardsAndMoves)

    val stream: Seq[(Board, Seq[Move])] = for {
      //      currentBoardsAndMoves <- workStack.dequeue()
      newBoardsAndMoves <- streamOfMoves(currentBoardsAndMoves, boardHistory)
    } yield {
      newBoardsAndMoves
    }

    //    println("new: " + stream)
    //    println("new: " + stream.size)
    //    val newBoardHistory = boardHistory ++ stream.map(_._1)
    val newBoardHistory = boardHistory :+ currentBoardsAndMoves._1

    //    boardsAndMoves.append(solveRec(stream, newBoardHistory))
    //    solveRec(boardsAndMoves.append(stream), newBoardHistory)
    workStack ++= stream
    //    println("ws: " + workStack.size)
    val s2 = stream.toStream

    s2.append(solveRec(workStack, newBoardHistory))
    //  Stream.cons( boardsAndMoves.head, solveRec(boardsAndMoves ++ (stream), workStack, newBoardHistory))
  }

  private def streamOfMoves(currentBoardAndMoves: boardWithMoves, boardHistory: Seq[Board]) = {
    val (currentBoard, moves) = currentBoardAndMoves
    if (currentBoard.isClean) {
      Seq.empty
    }
    else {
      val newMoves = doAllPossibleMoves(currentBoard)

      val newBoardsAndMoves = newMoves
        //        .withFilter({ case (_, m) => moves.isEmpty || Move.areOpposites(m, moves.head) == false })
        .withFilter({ case (board, _) => boardHistory.contains(board) == false })
        .map({ case (board, move) => (board, move +: moves) })

      newBoardsAndMoves
    }
  }

  private def doAllPossibleMoves(currentBoard: Board): Seq[(Board, Move)] = {
    val validMoves = currentBoard.validMoves
    validMoves.map(move => (currentBoard.moveBot(move), move._2))
  }


}


