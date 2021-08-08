---
title: From a Class to Game
chapter: 03 - Delegate things
author: Frédéric Delorme
description: Addng some specialized service into the game.
created: 2021-08-01
tags: gamedev, structure, input, window, render
---

## Delegating things

If we want to produce the game we will be able to code all thing into one class, but we will quickly see that the class will grow very fast according to new things we will add, as enemies, decors, player, and so on. Too much code in one place is a very bad practice.

The good one consists in creating multiple classes interacting, and having a dedicated goal:

- `InputHandler` will manager user/player input,
- `Render` will render object
- `Window` will support display and adaptation to the host machine,
- `GameObject` will be the main entity for our game.

### Input Handler

To be able for the user/player to interact with the game, we will add some 'connection' between the game window and our `Game` class.
the Java world request to implements some specific method in our classes to intercept Keyboard events: the `KeyListener` interface.

```java
public class Game implements KeyListener{


    public void keyTyped(KeyEvent e){}
    public void keyPressed(KeyEvent e){;
        System.out.println("key pressed: "+e.getKeyCode()):
    }
    public void keyReleased(KeyEvent e){
        System.out.println("key released: "+e.getKeyCode()):
    }
}
```

With those very basic events we will be able to manage keys input. but we need to connect this event handler to the window :

```java
    private void initialize(String[] argv){

        parseArgs(argv);
        // define the JFrame window title
        frame = new JFrame(this.title);

        // connect listener
        frame.addListener(this);

        ...
        // let show the window !
        frame.setVisible(true);
    }
```

if you run your Game class, you will get some consoles log output :

```txt
key pressed: 76
key typed: 0
key released: 76
key pressed: 72
key typed: 0
key released: 72
```

### the Window

If we let the **JFrame** into the main `Game` class, we will create to much interaction between the game and the JFrame.

So we need to delegate window management to a dedicated object: **Window**.

Extract from the Game `initialize()` method all the `JFrame` initialisation and move it to the `Window()` constructor.

let's create the Jframe, and connect the KeyListener.

```java
public class Window {

    private JFrame frame;

    public Window(String title, int width, int height) {
        frame = new JFrame(title);

        GraphicsDevice device = frame.getGraphicsConfiguration().getDevice();
        Dimension dim = new Dimension(width, height);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(dim);
        frame.setPreferredSize(dim);
        frame.setMaximumSize(dim);
        frame.addKeyListener(new InputHandler(this));
        frame.setLocation(new Point((int) (device.getDisplayMode().getWidth() - dim.width) / 2,
                (int) (device.getDisplayMode().getHeight() - dim.height) / 2));
        frame.pack();
        frame.setVisible(true);
    }
}
```

And from the `Game#initialize()` method :

```java
  public void initialize(String[] argv) {
    parseArgs(argv);
    ...
    window = new Window(this.title, (int) (this.width * this.scale), (int) (this.height * this.scale));

  }
```

### draw things

Like for managing input, we are going to create a class to delegate all drawing operation: the `Render` class.

This class has the particularity to draw all visual object to a buffer and will let the window display this buffer.

So first we will need a class with an image buffer.

```java
    private BufferedImage buffer;

    public Render(int width, int height) {

        this.buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    public void render() {
        Graphics2D g = this.buffer.createGraphics();
        g.clearRect(0, 0, this.buffer.getWidth(), this.buffer.getHeight());
        /// more thing to do n the next chapters
        g.dispose();
    }

    public BufferedImage getBuffer() {
        return this.buffer;
    }
```

So the class is in charge of creating and maintaining an internal draw buffer. The render() method will first clear this image buffer (with a black color), and will do more thing in the future chapters.
