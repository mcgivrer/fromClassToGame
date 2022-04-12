package features;

import fr.snapgames.fromclasstogame.core.Game;

import java.util.HashMap;
import java.util.Map;

public class CommonDefSteps {
    private static Map<String, Object> context = new HashMap<>();

    public static void add(String name, Object value) {
        context.put(name, value);
    }

    public static Object get(String name) {
        return context.get(name);
    }

    public static Game getGame() {
        return (Game) get("game");
    }

    public static Game setGame(Game game) {
        add("game", game);
        return game;
    }
}
