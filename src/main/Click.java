package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Click implements MouseListener {

    private static final Click click = new Click();
    private Click(){
    }

    public static Click getInstance() {
        return click;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (GamePanel.getInstance().inBoxX() != -1 && GamePanel.getInstance().inBoxY() != -1) {
            if(!Board.getInstance().gameLose) {
                Board.getInstance().revealBox(GamePanel.getInstance().inBoxX(),GamePanel.getInstance().inBoxY());
            }
        }

        if (GamePanel.getInstance().inUndoBox()) {
            Board.getInstance().undoMove();
        }
        
        if (GamePanel.getInstance().inResetBox()) {
        	Board.getInstance().resetGame();
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
