package fr.snapgames.fromclasstogame.core.io;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.config.Configuration;
import fr.snapgames.fromclasstogame.core.gfx.Window;
import fr.snapgames.fromclasstogame.core.system.System;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler extends System implements KeyListener {

    private Window window;

    private boolean[] keys = new boolean[65635];

    @Override
    public String getName() {
        return InputHandler.class.getName();
    }

    public InputHandler(Game g) {
        super(g);
    }

    @Override
    public int initialize(Configuration config) {
        return 0;
    }

    @Override
    public void dispose() {

    }

    public InputHandler setWindow(Window window) {
        this.window = window;
        window.getFrame().addKeyListener(this);
        return this;
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

    public void addKeyListener(KeyListener kl) {
        window.getFrame().addKeyListener(kl);
    }

}
