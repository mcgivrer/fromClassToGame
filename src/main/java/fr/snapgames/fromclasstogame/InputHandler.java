package fr.snapgames.fromclasstogame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {
    private Window window;

    public InputHandler(Window window) {
        this.window = window;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("key typed: " + e.getKeyCode());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("key pressed: " + e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("key released: " + e.getKeyCode());
    }

}
