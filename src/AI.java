import java.util.Random;

// This class picks moves
public class AI {
    private final Random rand = new Random();

    // RECURSION
    public String pick(Board<Player> board) {
        int row = rand.nextInt(3) + 1;
        int col = rand.nextInt(3) + 1;
        // If this is a valid (open) spot, return it
        if(board.getCell(row - 1, col - 1) == null) {
            return col + "," + row;
        }
        // If not, call the function again
        return pick(board);

        // This means that it will keep calling itself until it finds an open spot
        // There is not a set end condition, so in theory it could keep guessing full spots
        // and cause a recursionLimitException, but the chances of that happening are (8/9) ^ 7,000,
        // which is so small google just returns 0
    }
}
