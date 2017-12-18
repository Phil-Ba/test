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

    public static boolean areOpposites(Move m1, Move m2) {
        if (m1 == Left && m2 == Right) {
            return true;
        }
        if (m2 == Left && m1 == Right) {
            return true;
        }
        if (m2 == Up && m1 == Down) {
            return true;
        }
        if (m1 == Up && m2 == Down) {
            return true;
        }

        return false;
    }

}
