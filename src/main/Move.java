package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Move implements MouseMotionListener {

    private static final Move move = new Move();
    private Move(){
    }

    public static Move getInstance() {
        return move;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        GamePanel.getInstance().mouseX = e.getX();
        GamePanel.getInstance().mouseY = e.getY();
    }
}
