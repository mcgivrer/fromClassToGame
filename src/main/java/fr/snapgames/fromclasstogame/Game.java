package fr.snapgames.fromclasstogame;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

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
public class Game {

  private static final Logger logger = LoggerFactory.getLogger(Game.class);

  private ResourceBundle defaultConfig;

  private int width = 320;
  private int height = 200;
  private double scale = 1.0;
  private String title = "fromClassToGame";

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
  }

  public void loadDefaultValues() {
    defaultConfig = ResourceBundle.getBundle("config");
    this.width = Integer.parseInt(defaultConfig.getString("game.setup.width"));
    this.height = Integer.parseInt(defaultConfig.getString("game.setup.height"));
    this.scale = Double.parseDouble(defaultConfig.getString("game.setup.scale"));
    this.title = defaultConfig.getString("game.setup.title");
  }

  public void parseArgs(String[] argc) throws UnknownArgumentException {
    for (String arg : argc) {
      String[] values = arg.split("=");
      switch (values[0]) {
        case "width":
          this.width = Integer.parseInt(values[1]);
          break;
        case "height":
          this.height = Integer.parseInt(values[1]);
          break;
        case "scale":
          this.scale = Double.parseDouble(values[1]);
          break;
        case "title":
          this.title = values[1];
          break;
        default:
          throw new UnknownArgumentException(String.format("The argument %s is unknown", arg));
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
    Map<String, BufferedImage> images = readResources();

    GameObject player = new GameObject("player", 160, 100).setColor(Color.RED).setSpeed(0.02, 0.02).setSize(16.0, 16.0)
        .setImage(images.get("redBall"));
    for (int i = 0; i < 10; i++) {
      GameObject e = new GameObject("enemy_" + i, rand(0, 320), rand(0, 200))
          .setSpeed(rand(-0.05, 0.05), rand(-0.05, 0.05)).setColor(Color.ORANGE).setSize(8, 8)
          .setImage(images.get("orangeBall"));
      add(e);
    }
    add(player);
  }

  private Map<String, BufferedImage> readResources() {
    Map<String, BufferedImage> resources = new HashMap<>();
    resources.put("redBall", readImage("images/tiles.png", 0, 0, 16, 16));
    resources.put("orangeBall", readImage("images/tiles.png", 16, 0, 16, 16));
    return resources;
  }

  private BufferedImage readImage(String path, int x, int y, int w, int h) {
    BufferedImage image = null;
    try {
      image = ImageIO.read(Game.class.getClassLoader().getResourceAsStream(path)).getSubimage(x, y, w, h);
    } catch (IOException e) {
      logger.error("Unable to read resource", e);
    }
    return image;
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
    while (!exit && !testMode) {
      start = System.currentTimeMillis();
      dt = start - previous;
      input();
      update(dt);
      draw();
      previous = start;
    }
  }

  /**
   * Manage the input
   */
  private void input() {
    if (inputHandler.getKey(KeyEvent.VK_ESCAPE)) {
      this.exit = true;
    }
  }

  /**
   * Update all the game mechanism
   */
  private void update(long dt) {

    for (GameObject e : objectsList) {
      e.update(dt);
    }

  }

  /**
   * Draw the things from the game.
   */
  private void draw() {
    renderer.render();
    window.draw(renderer.getBuffer());
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

  public static void main(String[] argc) {
    try {
      Game game = new Game();
      game.run(argc);
    } catch (Exception e) {
      logger.error("Unable to run the game", e);
    }
  }

  public Window getWindow() {
    return window;
  }

  public GameObject find(String name) {
    return objects.get(name);
  }

  public List<GameObject> getObjectsList() {
    return objectsList;
  }

  public Render getRender() {
    return renderer;
  }

}
