package fr.snapgames.fromclasstogame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {

    private Window window;

    private boolean[] keys = new boolean[65635];

    public InputHandler(Window window) {
        this.window = window;
        window.getFrame().addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // nothing to do there !
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    public boolean getKey(int code) {
        return keys[code];
    }

}
