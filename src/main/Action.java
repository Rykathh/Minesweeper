package main;

import java.util.*;;

public class Action {
    private Stack<int[][]> undoStack;

    public void Minesweeper() {
        initialize the game board and mines
        board = new int[10][10];
        revealed = new boolean[10][10];
        undoStack = new Stack<int[][]>();
    }

    private void undoMove() {
        // restore the previous game state from the undo stack
        if (!undoStack.empty()) {
            int[][] prevState = undoStack.pop();
            for (int i = 0; i < 10; i++) {
                board[i] = Arrays.copyOf(prevState[i], 10);
            }
            // update the UI
            checkWin();
        }
    }

    private void makeMove(int row, int col) {
        // save the current game state to the undo stack
        int[][] prevState = new int[10][10];
        for (int i = 0; i < 10; i++) {
            prevState[i] = Arrays.copyOf(board[i], 10);
        }
        undoStack.push(prevState);

        // reveal the clicked square
        revealed[row][col] = true;
        buttons[row][col].setEnabled(false);
        if (board[row][col] == -1) {
            // game over
            gameOver();
        } else if (board[row][col] == 0) {
            // reveal adjacent squares recursively
            revealAdjacentSquares(row, col);
            checkWin();
        } else {
            // show the number of adjacent mines
            buttons[row][col].setText(Integer.toString(board[row][col]));
            checkWin();
        }
    }

    private void checkWin() {
        // check if all non-mine squares are revealed
        boolean allRevealed = true;
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                if (!revealed[row][col] && board[row][col] != -1) {
                    allRevealed = false;
                    break;
                }
            }
            if (!allRevealed) {
                break;
            }
        }
        if (allRevealed) {
            // game won
            statusLabel.setText("You win!");
            // reveal all squares and disable all buttons
            for (int row = 0; row < 10; row++) {
                for (int col = 0; col < 10; col++) {
                    revealed[row][col] = true;
                    buttons[row][col].setEnabled(false);
                    if (board[row][col] == -1) {
                        buttons[row][col].setText("X");
                    } else {
                        buttons[row][col].setText(Integer.toString(board[row][col]));
                    }
                }
            }
        }
    }

}
