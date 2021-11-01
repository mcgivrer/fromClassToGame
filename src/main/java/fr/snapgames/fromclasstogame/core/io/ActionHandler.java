package fr.snapgames.fromclasstogame.core.io;

import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.config.Configuration;
import fr.snapgames.fromclasstogame.core.gfx.Window;
import fr.snapgames.fromclasstogame.core.system.System;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The <code>ActionHandler</code> is implementing the <code>KeyListener</code> to
 * be the keyboard manager. It is mapping some <code>keyEvent</code> code to
 * possible <code>ACTIONS</code>.
 * <ul>
 * <li>the <code>keyMapping</code> are the map containing the mapping between
 * some KeyCode to <code>ACTIONS</code> values,</li>
 * <li>the internal <code>keys</code> map contains all keys status as boolean
 * values,</li>
 * <li>the <code>actions</code> contains all Action events produced.</li>
 * </ul>
 * <p>
 * To use this <code>InputHandler</code>, you must implement the
 * <code>GameActionListener</code> in your using class and the specific
 * <code>action(ACTIONS)</code> method to use the action handling. you can also
 * use the <code>getAction(ACTIONS)</code> as asynchronous access to the
 * actions map.
 *
 * @author Frédéric Delorme <frederic.delorme@gmail.com>
 * @link https://gist.github.com/mcgivrer/34641df0df83c023e9983157b61be8b8
 * @since 2021
 */
public class ActionHandler extends System implements KeyListener {

    private static final Logger logger = LoggerFactory.getLogger(ActionHandler.class);

    private Window window;

    public interface ActionListener extends KeyListener {
        public void onAction(ACTIONS action);
    }

    public enum ACTIONS {
        UP, LEFT, RIGHT, DOWN,
        FIRE1, FIRE2, FIRE3, FIRE4,
        LT_FIRE, LB_FIRE, RT_FIRE, RB_FIRE,
        START, HOME, POWER
    }

    /*
     * Internal key current status buffer
     */
    private final boolean[] keys = new boolean[65536];
    /*
     * Internal key previous status buffer
     */
    private final boolean[] previousKeys = new boolean[65536];

    private List<ActionListener> listeners = new CopyOnWriteArrayList<>();

    private Map<Integer, ACTIONS> keyMapping = new HashMap<>();
    private Map<ACTIONS, Boolean> actions = new HashMap<>();

    private boolean ctrl;
    private boolean shift;
    private boolean alt;
    private boolean altGr;

    /**
     * Initialization of the {@link ActionHandler}. Define a default Action mapping with
     * basic keys. The map can be adapted or changed.
     *
     * @param g
     */
    public ActionHandler(Game g) {
        super(g);
    }

    @Override
    public String getName() {
        return ActionHandler.class.getName();
    }

    @Override
    public int initialize(Configuration config) {
        this.keyMapping.put(KeyEvent.VK_ENTER, ACTIONS.START);
        this.keyMapping.put(KeyEvent.VK_HOME, ACTIONS.HOME);
        this.keyMapping.put(KeyEvent.VK_SCROLL_LOCK, ACTIONS.POWER);
        this.keyMapping.put(KeyEvent.VK_UP, ACTIONS.UP);
        this.keyMapping.put(KeyEvent.VK_DOWN, ACTIONS.DOWN);
        this.keyMapping.put(KeyEvent.VK_LEFT, ACTIONS.LEFT);
        this.keyMapping.put(KeyEvent.VK_RIGHT, ACTIONS.RIGHT);
        this.keyMapping.put(KeyEvent.VK_SPACE, ACTIONS.FIRE1);
        this.keyMapping.put(KeyEvent.VK_Y, ACTIONS.FIRE2);
        this.keyMapping.put(KeyEvent.VK_A, ACTIONS.FIRE3);
        this.keyMapping.put(KeyEvent.VK_B, ACTIONS.FIRE4);
        this.keyMapping.put(KeyEvent.VK_F1, ACTIONS.LT_FIRE);
        this.keyMapping.put(KeyEvent.VK_F2, ACTIONS.LB_FIRE);
        this.keyMapping.put(KeyEvent.VK_F3, ACTIONS.RT_FIRE);
        this.keyMapping.put(KeyEvent.VK_F4, ACTIONS.RB_FIRE);
        return 0;
    }

    @Override
    public void dispose() {
        this.actions.clear();
        this.keyMapping.clear();
        this.listeners.clear();
    }

    public void add(ActionListener kl) {
        listeners.add(kl);
    }

    public void remove(ActionListener kl) {
        listeners.remove(kl);
    }


    public ActionHandler setWindow(Window window) {
        this.window = window;
        window.getFrame().addKeyListener(this);
        return this;
    }

    /**
     * Define the new KeyMapping to map actions and keys.
     *
     * @param keyMapping
     */
    public void setActionMap(Map<Integer, ACTIONS> keyMapping) {
        this.keyMapping = keyMapping;
    }

    public void keyTyped(final KeyEvent e) {
        listeners.forEach(kl -> {
            kl.keyTyped(e);
        });
    }

    public void keyPressed(final KeyEvent e) {
        // capture current event
        this.previousKeys[e.getKeyCode()] = this.keys[e.getKeyCode()];
        this.keys[e.getKeyCode()] = true;
        // get modifier's key status
        updateModifiers(e);
        // Convert Key pressed to action
        if (keyMapping.containsKey(e.getKeyCode())) {
            actions.put(keyMapping.get(e.getKeyCode()), true);
            // Delegate action's event to registered listeners
            listeners.forEach(kl -> {
                kl.onAction(keyMapping.get(e.getKeyCode()));
            });
        }
        // Delegate key's event to registered listeners
        listeners.forEach(kl -> {
            kl.keyPressed(e);

        });
    }

    private void updateModifiers(KeyEvent e) {
        this.shift = e.getModifiersEx() == KeyEvent.SHIFT_DOWN_MASK;
        this.ctrl = e.getModifiersEx() == KeyEvent.CTRL_DOWN_MASK;
        this.alt = e.getModifiersEx() == KeyEvent.ALT_DOWN_MASK;
        this.altGr = e.getModifiersEx() == KeyEvent.ALT_GRAPH_DOWN_MASK;
    }

    public void keyReleased(final KeyEvent e) {
        // capture current event
        this.previousKeys[e.getKeyCode()] = this.keys[e.getKeyCode()];
        this.keys[e.getKeyCode()] = false;
        // get modifier's key status
        updateModifiers(e);
        // Convert Key released to action
        if (keyMapping.containsKey(e.getKeyCode())) {
            actions.put(keyMapping.get(e.getKeyCode()), false);
        }
        try {
            // Delegate key's event to registered listeners
            listeners.forEach(kl -> {
                kl.keyReleased(e);
            });
        } catch (IndexOutOfBoundsException ie) {
            logger.error("unable to delegate key event " + ie.getMessage());

        }
    }

    public boolean get(int key) {
        return keys[key];
    }

    /**
     * Return action status
     *
     * @return boolean value
     */
    public boolean getAction(ACTIONS actionCode) {
        return this.actions.containsKey(actionCode) ? this.actions.get(actionCode) : false;
    }

    public boolean getShift() {
        return shift;
    }

    public boolean getCtrl() {
        return ctrl;
    }

    public boolean getAlt() {
        return alt;
    }

    public boolean getAltGr() {
        return altGr;
    }

}