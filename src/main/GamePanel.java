package main;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;

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

    int[][] mines = new int[16][8];
    int[][] neighbors = new int[16][8];
    boolean[][] revealed = new boolean[16][8];
    boolean[][] flagged = new boolean[16][8];

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

    public void init() {
        for(int i = 0; i < 16; i++){
            for (int j = 0; j < 8; j++){
                if (rand.nextInt(100)<20){
                    mines[i][j] = 1;
                }
                else mines[i][j] = 0;
            }
        }

        for(int i = 0; i < 16; i++){
            for (int j = 0; j < 8; j++){
                neighbors[i][j] = 0;

                if(i == 0 && j == 0) {
                    if (mines[i+1][j] == 1){
                        neighbors[i][j] += 1;
                    }
                    if (mines[i][j+1] == 1){
                        neighbors[i][j] += 1;
                    }
                    if (mines[i+1][j+1] == 1){
                        neighbors[i][j] += 1;
                    }
                }
                else if(i == 0 && j == 7) {
                    if (mines[i][j] == 1){
                        neighbors[i][j] += 1;
                    }
                    if (mines[i][j-1] == 1){
                        neighbors[i][j] += 1;
                    }
                    if (mines[i+1][j-1] == 1){
                        neighbors[i][j] += 1;
                    }
                }
                else if(i == 15 && j == 0) {
                    if (mines[i-1][j] == 1){
                        neighbors[i][j] += 1;
                    }
                    if (mines[i-1][j+1] == 1){
                        neighbors[i][j] += 1;
                    }
                    if (mines[i][j+1] == 1){
                        neighbors[i][j] += 1;
                    }
                }
                else if(i == 15 && j == 7) {
                    if (mines[i-1][j] == 1){
                        neighbors[i][j] += 1;
                    }
                    if (mines[i-1][j-1] == 1){
                        neighbors[i][j] += 1;
                    }
                    if (mines[i][j-1] == 1){
                        neighbors[i][j] += 1;
                    }
                }
                else if (i == 0){
                    if (mines[i][j-1] == 1){
                        neighbors[i][j] += 1;
                    }
                    if (mines[i+1][j-1] == 1){
                        neighbors[i][j] += 1;
                    }
                    if (mines[i+1][j] == 1){
                        neighbors[i][j] += 1;
                    }
                    if (mines[i][j+1] == 1){
                        neighbors[i][j] += 1;
                    }
                    if (mines[i+1][j+1] == 1){
                        neighbors[i][j] += 1;
                    }
                }
                else if (i == 15){
                    if (mines[i-1][j-1] == 1){
                        neighbors[i][j] += 1;
                    }
                    if (mines[i][j-1] == 1){
                        neighbors[i][j] += 1;
                    }
                    if (mines[i-1][j] == 1){
                        neighbors[i][j] += 1;
                    }
                    if (mines[i-1][j+1] == 1){
                        neighbors[i][j] += 1;
                    }
                    if (mines[i][j+1] == 1){
                        neighbors[i][j] += 1;
                    }
                }
                else if (j == 7){
                    if (mines[i-1][j-1] == 1){
                        neighbors[i][j] += 1;
                    }
                    if (mines[i][j-1] == 1){
                        neighbors[i][j] += 1;
                    }
                    if (mines[i+1][j-1] == 1){
                        neighbors[i][j] += 1;
                    }
                    if (mines[i-1][j] == 1){
                        neighbors[i][j] += 1;
                    }
                    if (mines[i+1][j] == 1){
                        neighbors[i][j] += 1;
                    }
                }
                else if (j == 0){
                    if (mines[i-1][j] == 1){
                        neighbors[i][j] += 1;
                    }
                    if (mines[i+1][j] == 1){
                        neighbors[i][j] += 1;
                    }
                    if (mines[i-1][j+1] == 1){
                        neighbors[i][j] += 1;
                    }
                    if (mines[i][j+1] == 1){
                        neighbors[i][j] += 1;
                    }
                    if (mines[i+1][j+1] == 1){
                        neighbors[i][j] += 1;
                    }
                }
                else {
                    if (mines[i - 1][j - 1] == 1) {
                        neighbors[i][j] += 1;
                    }
                    if (mines[i][j - 1] == 1) {
                        neighbors[i][j] += 1;
                    }
                    if (mines[i + 1][j - 1] == 1) {
                        neighbors[i][j] += 1;
                    }
                    if (mines[i - 1][j] == 1) {
                        neighbors[i][j] += 1;
                    }
                    if (mines[i + 1][j] == 1) {
                        neighbors[i][j] += 1;
                    }
                    if (mines[i - 1][j + 1] == 1) {
                        neighbors[i][j] += 1;
                    }
                    if (mines[i][j + 1] == 1) {
                        neighbors[i][j] += 1;
                    }
                    if (mines[i + 1][j + 1] == 1) {
                        neighbors[i][j] += 1;
                    }
                }

                if (mines[i][j] == 1) {
                    neighbors[i][j] = -1;
                }
            }
        }
    }

    public void update() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.white);
        g2.fillRect(spacing, spacing,139,60-2*spacing);
        g2.fillRect(720+spacing, spacing,60-2*spacing,60-2*spacing);

        g2.setFont(new Font("Tahoma", Font.BOLD, 40));
        g2.setColor(Color.black);
        g2.drawString("UNDO",12,45);
        g2.drawString("FLAG",732,45);


        for(int i = 0; i < 16; i++){
            for (int j = 0; j < 8; j++){
                if (mines[i][j] == 1){
                    g2.setColor(Color.red);
                } else if (revealed[i][j]){
                    g2.setColor(Color.lightGray);
                } else
                    g2.setColor(Color.gray);
                g2.fillRect(spacing+i*60, spacing+j*60+60,60-2*spacing,60-2*spacing);

                if (revealed[i][j]){
                    g2.setColor(Color.black);
                    g2.drawString(Integer.toString(neighbors[i][j]),i*60+16,j*60+60+45);
                }
            }
        }

        if (inBoxX() != -1 && inBoxY() != -1) {
            int x = inBoxX();
            int y = inBoxY();
            g2.setColor(Color.white);
            g2.fillRect(spacing+x*60, spacing+y*60+60,60-2*spacing,60-2*spacing);
        }

        g2.dispose();
    }

    public int inBoxX() {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 8; j++) {
                if(mouseX >= spacing+i*60
                        && mouseX <= 60-spacing+i*60
                        && mouseY >= spacing + j*60 + 60
                        && mouseY <= j*80 + 120-spacing){
                    return i;
                }
            }
        }
        return -1;
    }

    public int inBoxY() {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 8; j++) {
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

    public class Click implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (inBoxX() != -1 && inBoxY() != -1) {
                revealed[inBoxX()][inBoxY()] = true;
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
