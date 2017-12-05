import scala.collection.mutable

/**
  *
  */
object Solution2 {

  //Write your code here
  val q: mutable.Queue[Char] = mutable.Queue[Char]()
  val s = mutable.ListBuffer[Char]()

  def pushCharacter(c: Char) = c +=: s

  def enqueueCharacter(c: Char) = q += c

  def popCharacter() = {
    val res = s.head
    s.remove(0)
    res
  }


  def dequeueCharacter() = q.dequeue()

  //Two instance variables: one for your , and one for your .
  //A void pushCharacter(char ch) method that pushes a character onto a stack.
  //A void enqueueCharacter(char ch) method that enqueues a character in the  instance variable.
  //A char popCharacter() method that pops and returns the character at the top of the  instance variable.
  //A char dequeueCharacter() method that dequeues and returns the first character in the  instance variable.

}
