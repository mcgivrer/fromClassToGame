package fr.snapgames.fromclasstogame;

/**
 * The {@link Scene} interface to create a new game state.
 * 
 * @author Frédéric Delorme
 * @since 2021-08-02
 */
public interface Scene {

    String getName();

    void initialize(Game g);

    void create(Game g);

    void activate();

    void update(long dt);

    void input();

    void render();

    void dispose();

}
