package main;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    public int mouseX;
    public int mouseY;
    final int spacing = 5;
    final int tileSize = 60;
    final int maxScreenCol = 16;
    final int maxScreenRow = 9;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

    final int FPS = 60;

    final int maxBoardCol = maxScreenCol;
    final int maxBoardRow = maxScreenRow-1;
    int[][] mines = new int[maxBoardCol][maxBoardRow];
    int[][] numbers = new int[maxBoardCol][maxBoardRow];
    boolean[][] revealed = new boolean[maxBoardCol][maxBoardRow];
    Stack<int[]> undoStack = new Stack<int[]>();

    boolean gameWin = false;
    boolean gameLose = false;

    public void undoMove() {
        // restore the previous game state from the undo stack
        if (!undoStack.empty()) {
            int[] prevState = undoStack.pop();
            revealed[prevState[0]][prevState[1]] = false;
        }
    }

    Random rand = new Random();

    Thread gameThread;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void init() {
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

    @Override
    public void run() {

        Move move = new Move();
        this.addMouseMotionListener(move);

        Click click = new Click();
        this.addMouseListener(click);

        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        init();

        while (gameThread != null) {
            update();
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;

                if (remainingTime < 0){
                    remainingTime = 0;
                }

                Thread.sleep((long)remainingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void update() {
        checkLose();
        checkWin();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // Draw undo button
        g2.setColor(Color.white);
        g2.fillRect(spacing, spacing,140,60-2*spacing);
        g2.setFont(new Font("Tahoma", Font.BOLD, 40));
        g2.setColor(Color.black);
        g2.drawString("UNDO",12,45);

        // Draw win/lose button
        if (gameLose) {
            g2.setColor(Color.white);
            g2.drawString("LOSE", 600, 45);
        } else if (gameWin) {
            g2.setColor(Color.white);
            g2.drawString("WIN",600,45);
        }

        // Draw board
        for(int i = 0; i < maxBoardCol; i++){
            for (int j = 0; j < maxBoardRow; j++){
                if (revealed[i][j] && mines[i][j] == 1){
                    g2.setColor(Color.red);
                } else if (revealed[i][j]){
                    g2.setColor(Color.lightGray);
                } else
                    g2.setColor(Color.gray);
                g2.fillRect(spacing+i*60, spacing+j*60+60,60-2*spacing,60-2*spacing);

                if (revealed[i][j] && mines[i][j] == 1){
                    g2.setColor(Color.black);
                    g2.drawString("*",i*60+maxBoardCol+2,j*60+60+50);
                } else if (revealed[i][j]){
                    g2.setColor(Color.black);
                    g2.drawString(Integer.toString(numbers[i][j]),i*60+maxBoardCol,j*60+60+45);
                }
            }
        }

        // Highlight mouse
        if (inBoxX() != -1 && inBoxY() != -1) {
            int x = inBoxX();
            int y = inBoxY();
            g2.setColor(Color.white);
            g2.fillRect(spacing+x*60, spacing+y*60+60,60-2*spacing,60-2*spacing);
        }

        g2.dispose();
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

    // Check position x-axis if in mines box
    public int inBoxX() {
        for (int i = 0; i < maxBoardCol; i++) {
            for (int j = 0; j < maxBoardRow; j++) {
                if(mouseX >= spacing+i*60
                    && mouseX <= 60-spacing+i*60
                    && mouseY >= spacing + j*60 + 60
                    && mouseY <= j*60 + 120-spacing){
                    return i;
                }
            }
        }
        return -1;
    }

    // Check position y-axis if in mines box
    public int inBoxY() {
        for (int i = 0; i < maxBoardCol; i++) {
            for (int j = 0; j < maxBoardRow; j++) {
                if(mouseX >= spacing+i*60
                    && mouseX <= 60-spacing+i*60
                    && mouseY >= spacing + j*60 + 60
                    && mouseY <= j*60 + 120-spacing){
                    return j;
                }
            }
        }
        return -1;
    }

    // Check position x-axis if in undo box
    public boolean inUndoBox() {
        if(mouseX >= spacing
            && mouseX <= 140+spacing
            && mouseY >= spacing
            && mouseY <= 60-spacing) {
            return true;
        }
        return false;
    }

    public void revealBox(int x, int y) {
        revealed[x][y] = true;
        int[] coordinate = {x,y};
        undoStack.push(coordinate);
    }

    // Click function for mouse
    public class Click implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (inBoxX() != -1 && inBoxY() != -1) {
                revealBox(inBoxX(),inBoxY());
            }

            if (inUndoBox()) {
                undoMove();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    // Move function for mouse
    public class Move implements MouseMotionListener {


        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
        }
    }

}
