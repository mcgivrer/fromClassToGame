package fr.snapgames.fromclasstogame;

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
public class Game {

  private static final Logger logger = LoggerFactory.getLogger(Game.class);

  private ResourceBundle defaultConfig;

  private int width = 320;
  private int height = 200;
  private double scale = 2.0;

  private String title = "fromClassToGame";

  public Window window;
  public Render renderer;

  public boolean exit = false;
  public boolean testMode = false;

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
  public void initialize(String[] argv) {

    loadDefaultValues();
    parseArgs(argv);

    renderer = new Render(this.width, this.height);
    window = new Window(this.title, (int) (this.width * this.scale), (int) (this.height * this.scale));
  }

  public void loadDefaultValues() {
    defaultConfig = ResourceBundle.getBundle("config");
    this.width = Integer.parseInt(defaultConfig.getString("game.setup.width"));
    this.height = Integer.parseInt(defaultConfig.getString("game.setup.height"));
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
   */
  public void run(String[] argv) {
    try {
      initialize(argv);
      loop();
      dispose();
    } catch (Exception e) {
      logger.error("Unable to run the game", e);
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
    // TODO implement an input management
  }

  /**
   * Update all the game mechanism
   */
  private void update(long dt) {
    // TODO update something
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
    // TODO if needed releae resources !
  }

  /**
   * Request the game to exit.
   */
  public void requestExit() {
    this.exit = true;
  }

  public static void main(String[] argc) {
    Game game = new Game();
    game.run(argc);
  }

}
