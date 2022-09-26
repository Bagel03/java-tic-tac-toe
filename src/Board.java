import java.lang.reflect.Array;
import java.util.ArrayList;

public class Board<T> {
    public enum Direction {
        DIAG_DOWN_TO_RIGHT,
        DIAG_DOWN_TO_LEFT,
        ROW,
        COL
    }
    protected ArrayList<ArrayList<T>> cells;

    public Board() {
        // CASTING
        cells = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            ArrayList<T> subArr = new ArrayList<>();
            for(int j = 0; j < 3; j++) {
                subArr.add(null);
            }
            cells.add(subArr);
        }
    }

    public Board cloneAndSet(int row, int col, T val) {
        Board clone = new Board();
        for(int i = 0; i< 3; i++) {
            for(int j = 0; j < 3; j++) {
                try {
                    clone.setCell(i, j, this.getCell(i, j));
                }catch (Exception e) {}
            }
        }
        try {
            clone.setCell(row, col, val);
        } catch (Exception e) {}
        return  clone;
    }

    // Helpers for individual cells
    public T getCell(int row, int col) {
        return cells.get(row).get(col);
    }
    public void setCell(int row, int col, T val) throws Exception {
        if(row > 2 || row < 0 || col > 2 || col < 0) {

            throw new Exception("Invalid coordinates: (" + (col+1) + ", " + (row+1) + "). Coordinates must be between 1 and 3!");
        }
        if(getCell(row, col) != null) {
            throw new Exception("(" + (col + 1) + ", " + (row+1) + ") is not empty!");
        }

        cells.get(row).set(col, val);
    }

    // Utils for scoring
    private ArrayList<T> getRow(int row) {
        // Here we run into something interesting, simply returning the sub-array will return a ref
        // we want this to be detached, even though it is never modified in our code,
        // it could have unintended side effects that are hard to debug,
        // so we make a new array and copy elements in individually instead of all at once

        ArrayList<T> rowItems = new ArrayList<>() ;
        // System.arraycopy could be used but that's harder to read imo.
        for(int i = 0; i < 3; i++) {
            rowItems.add( getCell(row, i));
        }
        return  rowItems;
    }
    private ArrayList<T>  getCol(int col) {
        ArrayList<T> colItems= new ArrayList<>() ;
        for(int i = 0; i < 3; i++) {
            colItems.add(getCell(i, col));
        }
        return  colItems;
    }
    private ArrayList<T>  getDiag(int downToLeft) {
        ArrayList<T> diagItems= new ArrayList<>() ;
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if((downToLeft == 0 && i == j) || (downToLeft == 1 && i == 2 - j)) {
                    diagItems.add(getCell(i, j));
                }
            }
        }
        return diagItems;
    }
    public ArrayList<T> getAllIn(Direction direction, int num) {
        return switch (direction) {
            case COL -> getCol(num);
            case ROW -> getRow(num);
            case DIAG_DOWN_TO_LEFT -> getDiag(1);
            case DIAG_DOWN_TO_RIGHT -> getDiag(0);
        };
    }

    // Just finds if the game is over (3 in a row or the board is full)
    public boolean isGameOver() {
        if(allInDirAreSame(Direction.DIAG_DOWN_TO_RIGHT, 0 ) != null) return true;
        if(allInDirAreSame(Direction.DIAG_DOWN_TO_LEFT, 0 ) != null) return true;

        for(int i = 0; i < 3; i++) {
            if(allInDirAreSame(Direction.ROW, i) != null) return true;
            if(allInDirAreSame(Direction.COL, i) != null) return true;
        }

        // Check for any empty spots (null)
        for(ArrayList<T> row : cells) {
            for(T player : row) {
                if(player == null) return false;
            }
        }

        // The board is full, and there are no 3 in a rows, it is over (tie)
        return true;
    }

    // Returns the player that is in all 3 spaces, if they are different returns null
    public T allInDirAreSame(Board.Direction dir, int num) {
        ArrayList<T> players = getAllIn(dir, num);

        if(players.stream().allMatch(player -> player == players.get(0))) {
            return players.get(0);
        }
        return null;
    }

    // Printing
    public String getAsString() {
        String boardStr = "";
        for(ArrayList<T> row : cells) {
            for(T player: row) {
                if(player == null) {
                    boardStr += "   |";
                    continue;
                }

                boardStr += " "+ player + " |";
            }
            boardStr = boardStr.substring(0, boardStr.length() - 2);
            boardStr += "\n-----------\n";
        }

        return boardStr.substring(0, boardStr.lastIndexOf("\n", boardStr.length() - 2));
    }
}
