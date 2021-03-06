package cleaning;

/**
 *
 */
public enum Move {
    Clean(0, 0),
    Left(-1, 0),
    Right(1, 0),
    Up(0, 1),
    Down(0, -1);

    public static final Move[] movingMoves = {Left, Right, Up, Down};

    public final int x;
    public final int y;

    Move(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
