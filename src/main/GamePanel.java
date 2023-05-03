package main;

import javax.swing.JPanel;
import java.awt.*;

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

    Thread gameThread;

    private GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
    }

    private static final GamePanel gamePanel = new GamePanel();
    public static GamePanel getInstance() {
        return gamePanel;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        this.addMouseMotionListener(Move.getInstance());
        this.addMouseListener(Click.getInstance());

        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            update();
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long)remainingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void update() {
        Board.getInstance().checkLose();
        Board.getInstance().checkWin();
        if (Board.getInstance().gameLose || Board.getInstance().gameWin) {
            Board.getInstance().revealAll();
        }
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

        //Draw reset button
        Graphics2D g3 = (Graphics2D) g;
        	
        g3.setColor(Color.yellow);
        g3.fillRect(180, 7, 150, 48);
        g3.setFont(new Font("Tahoma", Font.BOLD, 40));
        g3.setColor(Color.black);
        g3.drawString("RESET",190,45);
        
        // Draw win/lose button
        if (Board.getInstance().gameLose) {
            g2.setColor(Color.white);
            g2.drawString("LOSE", 600, 45);
        } else if (Board.getInstance().gameWin) {
            g2.setColor(Color.white);
            g2.drawString("WIN",600,45);
        }

        // Draw board
        for (int i = 0; i < Board.getInstance().maxBoardCol; i++) {
            for (int j = 0; j < Board.getInstance().maxBoardRow; j++) {
                if (Board.getInstance().revealed[i][j] && Board.getInstance().mines[i][j] == 1) {
                    g2.setColor(Color.red);
                } else if (Board.getInstance().revealed[i][j]) {
                    g2.setColor(Color.lightGray);
                } else
                    g2.setColor(Color.gray);
                g2.fillRect(spacing+i*60, spacing+j*60+60,60-2*spacing,60-2*spacing);

                if (Board.getInstance().revealed[i][j] && Board.getInstance().mines[i][j] == 1) {
                    g2.setColor(Color.black);
                    g2.drawString("*",i*60+Board.getInstance().maxBoardCol+2,j*60+60+50);
                } else if (Board.getInstance().revealed[i][j]) {
                    g2.setColor(Color.black);
                    g2.drawString(Integer.toString(Board.getInstance().numbers[i][j]),i*60+Board.getInstance().maxBoardCol,j*60+60+45);
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


    // Check position x-axis if in mines box
    public int inBoxX() {
        for (int i = 0; i < Board.getInstance().maxBoardCol; i++) {
            for (int j = 0; j < Board.getInstance().maxBoardRow; j++) {
                if(mouseX >= spacing+i*60
                    && mouseX <= 60-spacing+i*60
                    && mouseY >= spacing + j*60 + 60
                    && mouseY <= j*60 + 120-spacing) {
                    return i;
                }
            }
        }
        return -1;
    }

    // Check position y-axis if in mines box
    public int inBoxY() {
        for (int i = 0; i < Board.getInstance().maxBoardCol; i++) {
            for (int j = 0; j < Board.getInstance().maxBoardRow; j++) {
                if(mouseX >= spacing+i*60
                    && mouseX <= 60-spacing+i*60
                    && mouseY >= spacing + j*60 + 60
                    && mouseY <= j*60 + 120-spacing) {
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
    
    public boolean inResetBox() {
        if(mouseX >= 180
            && mouseX <= 330
            && mouseY >= spacing
            && mouseY <= 48-spacing) {
            return true;
        }
        return false;
    }
}
