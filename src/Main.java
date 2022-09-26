import java.util.ArrayList;
import java.util.List;

/** Edward Badel, tic-tac-toe sample project
*   This is a simple tic-tac-toe game that runs in the console
 *
 *   Each turn, the player will pick a spot "x,y" to place an "X"
 *   and the computer will choose where to put an "O"
 *   The normal rules of tic-tac-toe are followed, and when someone wins
 *   (or the board is full) it prints "Game Over!"
 *
 *
 *   Files:
 *      Player.java
 *          Can be ignored, an enum for X or Y that the board uses
 *      AI.java
 *          A class that uses a recursive* method of choosing a valid,
 *          random place to make its next move
 *
 */

public class Main {
    private static TicTacToe game;

    public static void main(String[] args) {
        new TicTacToe().startGame();

    }


}