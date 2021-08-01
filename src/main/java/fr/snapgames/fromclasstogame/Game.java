package fr.snapgames.fromclasstogame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.snapgames.fromclasstogame.exceptions.UnknownArgumentException;

/**
 * Project: From Class To Game
 *
 * A First class to build a game attempt.
 *
 * @author Frédéric Delorme<frederic.delorme@gmail.com>
 * @since 0.0.1
 */
public class Game implements KeyListener {

  private static final Logger logger = LoggerFactory.getLogger(Game.class);

  private ResourceBundle defaultConfig;

  private int width = 320;
  private int height = 200;
  private double scale = 1.0;
  private double FPS = 60;
  private long realFPS = 60;

  private String title = "fromClassToGame";

  public Window window;
  public Render renderer = new Render(320, 200);
  public InputHandler inputHandler;

  public boolean exit = false;
  public boolean testMode = false;

  private String scenes = "";
  private SceneManager sceneManager;

  /**
   * the mandatory default constructor
   */
  public Game() {
  }

  /**
   * Initialize a game with some attributes.
   *
   * @param t title for the game window
   * @param w width of the game window
   * @param h heigth of the game window
   */
  public Game(String t, int w, int h) {
    title = t;
    width = w;
    height = h;
  }

  public void loadDefaultValues() {
    defaultConfig = ResourceBundle.getBundle("config");

    this.width = Integer.parseInt(defaultConfig.getString("game.setup.width"));
    this.height = Integer.parseInt(defaultConfig.getString("game.setup.height"));
    this.scale = Double.parseDouble(defaultConfig.getString("game.setup.scale"));
    this.FPS = Double.parseDouble(defaultConfig.getString("game.setup.fps"));
    this.title = defaultConfig.getString("game.setup.title");
    this.scenes = defaultConfig.getString("game.setup.scenes");
  }

  public void parseArgs(String[] argv) throws UnknownArgumentException {
    if (argv != null) {
      for (String arg : argv) {
        String[] values = arg.split("=");
        switch (values[0].toLowerCase()) {
          case "width":
            this.width = Integer.parseInt(values[1]);
            break;
          case "height":
            this.height = Integer.parseInt(values[1]);
            break;
          case "scale":
            this.scale = Double.parseDouble(values[1]);
            break;
          case "fps":
            this.FPS = Double.parseDouble(values[1]);
            break;
          case "title":
            this.title = values[1];
            break;
          default:
            throw new UnknownArgumentException(String.format("The argument %s is unknown", arg));
        }
      }
    }
  }

  /**
   * Initialization of the display window and everything the game will need.
   */
  public void initialize(String[] argv) throws UnknownArgumentException {

    loadDefaultValues();
    parseArgs(argv);

    renderer = new Render(this.width, this.height);
    window = new Window(this.title, (int) (this.width * this.scale), (int) (this.height * this.scale));
    inputHandler = new InputHandler(window);
    inputHandler.addKeyListener(this);
    sceneManager = new SceneManager(this);
    sceneManager.initialize(this.scenes.split(","));
  }

  /**
   * Entrypoint for the game. can parse the argc from the java command line.
   * 
   * @throws UnknownArgumentException
   */
  public void run(String[] argv) throws UnknownArgumentException {
    initialize(argv);
    createScene();
    loop();
    dispose();
  }

  private void createScene() {
    sceneManager.activate("demo");
  }

  /**
   * the famous main game loop where everything happend.
   */
  private void loop() {
    long start = System.currentTimeMillis();
    long previous = start;
    long dt = 0;
    long frames = 0;
    long timeFrame = 0;

    long frameDuration = (long) (1000 / FPS);
    while (!exit && !testMode) {
      start = System.currentTimeMillis();
      dt = start - previous;
      input();
      update(dt);
      draw();
      frames++;
      timeFrame += dt;
      if (timeFrame > 1000) {
        timeFrame = 0;
        realFPS = frames;
        frames = 0;
      }
      long elapsed = System.currentTimeMillis() - start;
      if (elapsed > 0 && elapsed < frameDuration) {
        try {
          Thread.sleep(frameDuration - (System.currentTimeMillis() - start));
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      previous = start;
    }
  }

  /**
   * Manage the input
   */
  private void input() {
    sceneManager.getCurrent().input();
  }

  /**
   * Update all the game mechanism
   */
  private void update(long dt) {
    sceneManager.getCurrent().update(dt);
  }

  /**
   * Draw the things from the game.
   */
  private void draw() {
    renderer.render();
    window.draw(realFPS, renderer.getBuffer());
  }

  /**
   * Free everything
   */
  private void dispose() {

    renderer.clear();
    window.close();
  }

  /**
   * Request the game to exit.
   */
  public void requestExit() {
    this.exit = true;
  }

  public Window getWindow() {
    return window;
  }

  public Render getRender() {
    return renderer;
  }

  public static void main(String[] argc) {
    try {
      Game game = new Game();
      game.run(argc);
    } catch (Exception e) {
      logger.error("Unable to run the game", e);
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {
    // nothing to do now
  }

  @Override
  public void keyPressed(KeyEvent e) {
    // nothing to do now

  }

  @Override
  public void keyReleased(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_F11:
        renderer.saveScreenshot();
        break;
      case KeyEvent.VK_ESCAPE:
        this.exit = true;
        break;
      default:
        break;
    }

  }

  public SceneManager getSceneManager() {
    return sceneManager;
  }
}
