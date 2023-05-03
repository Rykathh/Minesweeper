package main;

import java.util.Random;
import java.util.Stack;

public class Board {
    final int maxBoardCol = 16;
    final int maxBoardRow = 8;
    int[][] mines = new int[maxBoardCol][maxBoardRow];
    int[][] numbers = new int[maxBoardCol][maxBoardRow];
    boolean[][] revealed = new boolean[maxBoardCol][maxBoardRow];
    Stack<int[]> undoStack = new Stack<int[]>();

    boolean gameWin = false;
    boolean gameLose = false;

    private static final Board board = new Board();
    private Board(){
        init();
    }

    public static Board getInstance() {
        return board;
    }

    // Undo function
    public void undoMove() {
        // restore the previous game state from the undo stack
        if (!undoStack.empty()) {
            int[] prevState = undoStack.pop();
            revealed[prevState[0]][prevState[1]] = false;
        }
    }

    // Implement check for win conditions
    public void checkWin () {
        for (int i = 0; i < maxBoardCol; i++) {
            for (int j = 0;j < maxBoardRow ; j++) {
                if (numbers[i][j]>0) {
                    if (!revealed[i][j]){
                        gameWin = false;
                        return;
                    }
                }
            }
        }
        gameWin = true;
    }

    // Implement check for lose conditions
    public void checkLose () {
        gameLose = false;
        for (int i = 0; i < maxBoardCol; i++) {
            for (int j = 0;j < maxBoardRow ; j++) {
                if (numbers[i][j]<0) {
                    if (revealed[i][j]){
                        gameLose = true;
                        break;
                    }
                }
            }
        }
    }

    public void revealBox(int x, int y) {
        revealed[x][y] = true;
        int[] coordinate = {x,y};
        undoStack.push(coordinate);
    }

    public void init() {
        Random rand = new Random();
        for(int i = 0; i < maxBoardCol; i++){
            for (int j = 0; j < maxBoardRow; j++){
                if (rand.nextInt(100)<15){
                    mines[i][j] = 1;
                }
                else mines[i][j] = 0;
            }
        }

        for(int i = 0; i < maxBoardCol; i++){
            for (int j = 0; j < maxBoardRow; j++){
                numbers[i][j] = 0;

                if(i == 0 && j == 0) {
                    if (mines[i+1][j] == 1){
                        numbers[i][j] += 1;
                    }
                    if (mines[i][j+1] == 1){
                        numbers[i][j] += 1;
                    }
                    if (mines[i+1][j+1] == 1){
                        numbers[i][j] += 1;
                    }
                }
                else if(i == 0 && j == 7) {
                    if (mines[i][j] == 1){
                        numbers[i][j] += 1;
                    }
                    if (mines[i][j-1] == 1){
                        numbers[i][j] += 1;
                    }
                    if (mines[i+1][j-1] == 1){
                        numbers[i][j] += 1;
                    }
                }
                else if(i == 15 && j == 0) {
                    if (mines[i-1][j] == 1){
                        numbers[i][j] += 1;
                    }
                    if (mines[i-1][j+1] == 1){
                        numbers[i][j] += 1;
                    }
                    if (mines[i][j+1] == 1){
                        numbers[i][j] += 1;
                    }
                }
                else if(i == 15 && j == 7) {
                    if (mines[i-1][j] == 1){
                        numbers[i][j] += 1;
                    }
                    if (mines[i-1][j-1] == 1){
                        numbers[i][j] += 1;
                    }
                    if (mines[i][j-1] == 1){
                        numbers[i][j] += 1;
                    }
                }
                else if (i == 0){
                    if (mines[i][j-1] == 1){
                        numbers[i][j] += 1;
                    }
                    if (mines[i+1][j-1] == 1){
                        numbers[i][j] += 1;
                    }
                    if (mines[i+1][j] == 1){
                        numbers[i][j] += 1;
                    }
                    if (mines[i][j+1] == 1){
                        numbers[i][j] += 1;
                    }
                    if (mines[i+1][j+1] == 1){
                        numbers[i][j] += 1;
                    }
                }
                else if (i == 15){
                    if (mines[i-1][j-1] == 1){
                        numbers[i][j] += 1;
                    }
                    if (mines[i][j-1] == 1){
                        numbers[i][j] += 1;
                    }
                    if (mines[i-1][j] == 1){
                        numbers[i][j] += 1;
                    }
                    if (mines[i-1][j+1] == 1){
                        numbers[i][j] += 1;
                    }
                    if (mines[i][j+1] == 1){
                        numbers[i][j] += 1;
                    }
                }
                else if (j == 7){
                    if (mines[i-1][j-1] == 1){
                        numbers[i][j] += 1;
                    }
                    if (mines[i][j-1] == 1){
                        numbers[i][j] += 1;
                    }
                    if (mines[i+1][j-1] == 1){
                        numbers[i][j] += 1;
                    }
                    if (mines[i-1][j] == 1){
                        numbers[i][j] += 1;
                    }
                    if (mines[i+1][j] == 1){
                        numbers[i][j] += 1;
                    }
                }
                else if (j == 0){
                    if (mines[i-1][j] == 1){
                        numbers[i][j] += 1;
                    }
                    if (mines[i+1][j] == 1){
                        numbers[i][j] += 1;
                    }
                    if (mines[i-1][j+1] == 1){
                        numbers[i][j] += 1;
                    }
                    if (mines[i][j+1] == 1){
                        numbers[i][j] += 1;
                    }
                    if (mines[i+1][j+1] == 1){
                        numbers[i][j] += 1;
                    }
                }
                else {
                    if (mines[i - 1][j - 1] == 1) {
                        numbers[i][j] += 1;
                    }
                    if (mines[i][j - 1] == 1) {
                        numbers[i][j] += 1;
                    }
                    if (mines[i + 1][j - 1] == 1) {
                        numbers[i][j] += 1;
                    }
                    if (mines[i - 1][j] == 1) {
                        numbers[i][j] += 1;
                    }
                    if (mines[i + 1][j] == 1) {
                        numbers[i][j] += 1;
                    }
                    if (mines[i - 1][j + 1] == 1) {
                        numbers[i][j] += 1;
                    }
                    if (mines[i][j + 1] == 1) {
                        numbers[i][j] += 1;
                    }
                    if (mines[i + 1][j + 1] == 1) {
                        numbers[i][j] += 1;
                    }
                }

                if (mines[i][j] == 1) {
                    numbers[i][j] = -1;
                }
            }
        }
    }
}
