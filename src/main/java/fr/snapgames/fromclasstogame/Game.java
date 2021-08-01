package fr.snapgames.fromclasstogame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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

  private int score = 0;

  public Window window;
  public Render renderer = new Render(320, 200);
  public InputHandler inputHandler;

  public boolean exit = false;
  public boolean testMode = false;

  Map<String, GameObject> objects = new HashMap<>();
  List<GameObject> objectsList = new ArrayList<>();

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
  }

  public void loadDefaultValues() {
    defaultConfig = ResourceBundle.getBundle("config");
    this.width = Integer.parseInt(defaultConfig.getString("game.setup.width"));
    this.height = Integer.parseInt(defaultConfig.getString("game.setup.height"));
    this.scale = Double.parseDouble(defaultConfig.getString("game.setup.scale"));
    this.FPS = Double.parseDouble(defaultConfig.getString("game.setup.fps"));
    this.title = defaultConfig.getString("game.setup.title");
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
    // add main character (player)
    GameObject player = new GameObject("player", 160, 100).setColor(Color.RED).setSpeed(0.02, 0.02).setSize(16.0, 16.0)
        .setImage(ResourceManager.getSlicedImage("images/tiles.png", "player", 0, 0, 16, 16));
    add(player);
    // Add enemies(enemy_99)
    for (int i = 0; i < 10; i++) {
      GameObject e = new GameObject("enemy_" + i, rand(0, 320), rand(0, 200))
          .setSpeed(rand(-0.05, 0.05), rand(-0.05, 0.05)).setColor(Color.ORANGE).setSize(8, 8)
          .setImage(ResourceManager.getSlicedImage("images/tiles.png", "orangeBall", 16, 0, 16, 16));
      add(e);
    }
    Font f = ResourceManager.getFont("fonts/FreePixel.ttf").deriveFont(Font.CENTER_BASELINE, 14);
    // add some fixed text.
    TextObject scoreTO = new TextObject("score", 10, 20).setText("00000").setFont(f);
    scoreTO.setColor(Color.WHITE);
    scoreTO.priority = 10;
    add(scoreTO);
  }

  public double rand(double min, double max) {
    return (Math.random() * (max - min)) + min;
  }

  public void add(GameObject go) {
    if (!objects.containsKey(go.name)) {
      objects.put(go.name, go);
      objectsList.add(go);
      renderer.add(go);
    }
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
    if (inputHandler.getKey(KeyEvent.VK_ESCAPE)) {

    }
  }

  /**
   * Update all the game mechanism
   */
  private void update(long dt) {

    for (GameObject e : objectsList) {
      e.update(dt);
    }
    TextObject scoreTO = (TextObject) objects.get("score");
    scoreTO.setText(String.format("%05d", score));
    score++;
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
    objects.clear();
    objectsList.clear();
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

  public GameObject getGameObject(String name) {
    return objects.get(name);
  }

  /**
   * find GameObject filtered on their name according to a filteredName.
   * 
   * @param filteredName
   * @return
   */
  public List<GameObject> find(String filteredName) {
    return objectsList.stream().filter(o -> o.name.contains(filteredName)).collect(Collectors.toList());
  }

  public List<GameObject> getObjectsList() {
    return objectsList;
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
}
