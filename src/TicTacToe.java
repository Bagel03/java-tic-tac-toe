import java.beans.Expression;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

// INHERITANCE
public class TicTacToe extends Board<Player> {
    private Player currentPlayer;
    private AI ai;
    private final Scanner scanner;

    public TicTacToe() {
        super();
        currentPlayer = Player.X;
        ai = new AI();
        scanner = new Scanner(System.in);
    }

    public void startGame() {
        System.out.println("Do you want to play in hard mode?(y/n): ");
        String yn = scanner.nextLine();
        if(yn.toLowerCase().trim().replaceAll(" ", "").equals("y")) {
            this.ai = new SmartAI();
        }

        while(!isGameOver()) {
            if(currentPlayer == Player.X) {
                // Human
                humanMove();
            } else {
                aiMove();
            }
            currentPlayer = ( currentPlayer == Player.X ? Player.O : Player.X);
        }
        System.out.println("Game Over!");
        System.out.println(getAsString());
    }

    // PRIMITIVE TYPES
    public void setFromString(String pos, Player player) throws Exception {
        String[] pair = pos.replaceAll(" ", "").split(",");

        if(pair.length != 2) throw new Exception("Position should be in the format \"x, y\"!");
        int row = Integer.parseUnsignedInt(pair[1]) - 1;
        int col = Integer.parseUnsignedInt(pair[0]) - 1;

        setCell(row, col, player);
    }
    public void humanMove() {
        // Keep asking until they give a valid answer
        while(true) {
            // Also escape characters (I think I saw that in the syllabus somewhere)
            System.out.println(getAsString());
            System.out.print("Input your position as \"x, y\": ");
            try {
                setFromString(scanner.nextLine(), Player.X);
            } catch (Exception e) {
                System.out.println(e.getMessage() + "\nPlease try again");
                continue;
            }
        break;
        }
    }

    public void aiMove() {
        try {
            setFromString(ai.pick(this), Player.O);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
