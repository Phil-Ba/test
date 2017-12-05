package cleaning;

/**
 *
 */
public enum Move {
    Left(-1, 0),
    Right(1, 0),
    Up(0, 1),
    Down(0, -1);

    public final int x;
    public final int y;

    Move(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
