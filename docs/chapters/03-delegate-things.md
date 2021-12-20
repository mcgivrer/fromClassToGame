# Delegating things

If we want to produce the game we will be able to code all thing into one class, but we will quickly see that the class
will grow very fast according to new things we will add, as enemies, decors, player, and so on. Too much code in one
place is a very bad practice.

The good one consists in creating multiple classes interacting, and having a dedicated goal:

- `InputHandler` will manager user/player input,
- `Render` will render object
- `Window` will support display and adaptation to the host machine,
- `GameObject` will be the main entity for our game.

## Input Handler

To be able for the user/player to interact with the game, we will add some 'connection' between the game window and
our `Game` class. the Java world request to implements some specific method in our classes to intercept Keyboard events:
the `KeyListener` interface.

```java
public class Game implements KeyListener {


    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        ;
        System.out.println("key pressed: " + e.getKeyCode()):
    }

    public void keyReleased(KeyEvent e) {
        System.out.println("key released: " + e.getKeyCode()):
    }
}
```

With those very basic events we will be able to manage keys input. but we need to connect this event handler to the
window :

```java
    private void initialize(String[]argv){

        parseArgs(argv);
        // define the JFrame window title
        frame=new JFrame(this.title);

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
  public void initialize(String[]argv){
        parseArgs(argv);
        ...
        window=new Window(this.title,(int)(this.width*this.scale),(int)(this.height*this.scale));

        }
```

> **TIPS**<br/>To discover functional test for the `Window` object, see the scenario [Game_has_a_window.feature](../../src/test/resources/features/Game_has_a_Window.feature).

## Draw things

Like for managing input, we are going to create a class to delegate all drawing operation: the `Render` class.

This class has the particularity to draw all visual object to a buffer and will let the window display this buffer.

So first we will need a class with an image buffer.

```java
    private BufferedImage buffer;

public Render(int width,int height){

        this.buffer=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        }

public void render(){
        Graphics2D g=this.buffer.createGraphics();
        g.clearRect(0,0,this.buffer.getWidth(),this.buffer.getHeight());
        /// more thing to do n the next chapters
        g.dispose();
        }

public BufferedImage getBuffer(){
        return this.buffer;
        }
```

So the class is in charge of creating and maintaining an internal draw buffer. The render() method will first clear this
image buffer (with a black color), and will do more thing in the future chapters.

## extending things

To rise this simple Window, we are going to add some fancy features like:

- Saving a window capture to a file ins JPG or PNG file format,
- Switching the Window display from a windowed mode to a full screen mode.

These 2 features are really easy to implement. The capture mode can be implemented using the ImageIO capabilities into
the `Render` class, the mod switching will be managed into the `Window` class.

### Screen capture

First of all, get the default path where to save screenshots. Then build the `screenshots` directory, and finally, copy
the BufferedImage to a PNG file.

```java
class Render {
    ...

    public void saveScreenshot() {
        final String path = this.getClass().getResource("/").getPath();
        Path targetDir = Paths.get(path + "/screenshots");
        int i = screenShotIndex++;
        String filename = String.format("%sscreenshots/%s-%d.png", path, java.lang.System.nanoTime(), i);

        try {
            if (!Files.exists(targetDir)) {
                Files.createDirectory(targetDir);
            }
            File out = new File(filename);
            ImageIO.write(getBuffer(), "PNG", out);

            logger.info("Write screenshot to {}", filename);
        } catch (IOException e) {
            logger.error("Unable to write screenshot to {}:{}", filename, e.getMessage());
        }
    }
    ...
}
```

### Mode switching

The window mode is the default JFrame behavior, so nothing to do with that, just use `JFrame#setVisible()` capability.

But yo activate the full screen mode, we need to gather some graphic device information:

```java
class Window {
    ...
    boolean fullscreen;
    ...

    public void switchFullScreen() {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();
        if (!fullscreen && frame.isActive() && device.isFullScreenSupported()) {
            device.setFullScreenWindow(frame);
            fullscreen = true;
        } else {
            device.setFullScreenWindow(null);
            frame.setVisible(true);
            fullscreen = false;
        }
    }
}
```

So first get the default `GraphicsEnvironment` to retrieve the current active `GraphicsDevice`. then use
this `GraphicsDevice` to make our JFrame the content of this device.

Switching back to the Windowed mode will consists in removeing the `JFrame` from the current device content, and set
a `setVisible()` on our frame.

> **TIPS**<br/>The `Window` full screen specification are tested with [Window_can_go_fullscreen.feature](../../src/test/resources/features/Window_can_go_fullscreen.feature)