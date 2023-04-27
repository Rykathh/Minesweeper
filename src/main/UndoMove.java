package main;
import java.util.*;

public class UndoMove {

    private Stack<int[][]> undoStack;
    private int[][] board;

    public void Minesweeper() {
        // initialize the game board and mines
        undoStack = new Stack<int[][]>();
        board = new int[10][10];
    }

    public void undoMove() {
        if (!undoStack.empty()) {
            int[][] prevState = undoStack.pop();
            for (int i = 0; i < 10; i++) {
                board[i] = Arrays.copyOf(prevState[i], 10);
            }
            // update the UI
            
        }
    }
}
