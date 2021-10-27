# Input handler

The best way to manage user input, although we already implement something in the Game class, we will create a brand-new thing: The `InputHandler`.
The goal of this class is to capture events, and maintain a buffer of keys state, a boolean buffer.

```java
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
```

All is in this super simple class. `keyPressed` and `keyReleased` are setting true and false the corresponding boolean in the `keys` buffer.

To detect if a specific key has been pressed, just use `getKey(code)` !
