import java.util.ArrayList;

public class SmartAI extends AI {
    // This just gives us a numeric value of how favorable the board is to O
    // An unfinished game is 0, a X win is -1, an O win is 1, and a tie is 0
    private int staticEvaluation(Board<Player> board) {
        if(!board.isGameOver()) return 0;

        if(board.allInDirAreSame(Board.Direction.DIAG_DOWN_TO_RIGHT, 0 ) == Player.X) return -1;
        if(board.allInDirAreSame(Board.Direction.DIAG_DOWN_TO_LEFT, 0 ) == Player.X) return -1;

        if(board.allInDirAreSame(Board.Direction.DIAG_DOWN_TO_RIGHT, 0 ) == Player.O) return 1;
        if(board.allInDirAreSame(Board.Direction.DIAG_DOWN_TO_LEFT, 0 ) == Player.O) return 1;

        for(int i = 0; i < 3; i++) {
            if(board.allInDirAreSame(Board.Direction.ROW, i) == Player.X) return -1;
            if(board.allInDirAreSame(Board.Direction.COL, i) == Player.X) return -1;
            if(board.allInDirAreSame(Board.Direction.ROW, i) == Player.O) return 1;
            if(board.allInDirAreSame(Board.Direction.COL, i) == Player.O) return 1;
        }

        return 0;
    }

    private ArrayList<Board<Player>> getChildren(Board<Player> board, Player nextToPlay) {
        ArrayList<Board<Player>> children = new ArrayList<>();
        try {
            for(int i = 0; i< 3; i++) {
                for(int j = 0; j < 3; j++) {
                    if(board.getCell(i,j) == null) {
                        children.add(board.cloneAndSet(i,j, nextToPlay));
                    }
                }
            }
        } catch (Exception e) {}
        return children;
    }

    // The actual minimax function
    private int miniMax(Board<Player> currentBoard, boolean maximizingPlayer) {
        if(currentBoard.isGameOver()) {
            return staticEvaluation(currentBoard);
        }

        Player toPlay = maximizingPlayer ? Player.X : Player.O;
        if(maximizingPlayer) {
            int maxEval = -2;
            for(Board<Player> child : getChildren(currentBoard, toPlay)) {
                maxEval = Math.max(maxEval, miniMax(child, false));
            }
            return maxEval;
        } else {
            int minEval = 2;
            for(Board<Player> child : getChildren(currentBoard, toPlay)) {
                minEval = Math.min(minEval, miniMax(child, true));
            }
            return  minEval;
        }
    }

    public String pick(Board<Player> board) {
        int maxEval = -2;
        String bestMove = "";
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(board.getCell(i, j) == null) {
                    int eval = miniMax(board.cloneAndSet(i, j, Player.O), true);
                    if(eval > maxEval) {
                        bestMove = (j + 1) + "," + (i+1);
                        maxEval = eval;
                    }
                }
            }
        }
        return bestMove;
    }

}
