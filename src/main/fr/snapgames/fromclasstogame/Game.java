package fr.snapgames.fromclasstogame;

/**
 * Project: From Class To Game
 *
 * A First class to build a game attempt.
 *
 * @author Frédéric Delorme<frederic.delorme@gmail.com>
 * @since 0.0.1
 */
public class Game{
  
  private int width = 320;
  private int height = 200;
  private title = "fromClassToGame";
  
  private JFrame frame;
  
  /**
   * the mandatory default constructor
   */
  public Game(){
  }
  
  /**
   * Initialize a game with some attributes.
   *
   * @param title title for the game window
   * @param w width of the game window
   * @param h heigth of the game window
   */
  public Game(String title,int w, int h){
    title = title;
    width = w;
    heght = h;
  }
  
  /**
   * Initialization of the display window and everything the game will need.
   */
  public void initialize(){
    frame = new JFrame(this.title);
    Dimension dim = new Dimension(this.width,this.height);
    frame.setSize(dim);
    frame.setPreferredSize(dim);
    frame.setMaximumSize(dim);
    frame.pack();
    frame.setVisible(true);
  }
  
  /**
   * Entrypoint for the game. can parse the argc from the java command line.
   */
  public void run(String[] argc){
    initialize() ;
    loop();
    dispose();
  }
  
  /**
   * the famous main game loop where everything happend.
   */
  private void loop(){
    long start = System.currentMillis();
    long previous = start;
    long dt = 0;
    while(!exit){
      start = System.currentMillis();
      long dt = start-previous;
      input();
      update(dt);
      draw();
      previous = start;
    }
  }

  /**
   * Manage the input
   */
  private void input(){
    // TODO implement an input management
  }
  
  /**
   * Update all the game mechanism
   */
  private void update(int dt){
    // TODO update something
  }
  /**
   * Draw the things from the game.
   */
  private void draw(){
    // TODO draw something !
  }
  
  /**
   * Free everything
   */
  private void dispose(){
    // TODO if needed releae resources !
  }
    
  
  
  public static void main(String[] argc){
    Game game = new Game();
    game.run(argc);
  }
}
