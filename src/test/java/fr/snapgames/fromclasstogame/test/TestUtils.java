package fr.snapgames.fromclasstogame.test;

import fr.snapgames.fromclasstogame.core.scenes.Scene;
import fr.snapgames.fromclasstogame.core.scenes.SceneManager;
import fr.snapgames.fromclasstogame.core.system.SystemManager;

public class TestUtils {


    /**
     * Retrieve a Scene <code>sceneName</code> from the SceneManager
     *
     * @param sceneName Name of the scene to retrieve from the scene manager
     * @return the {@link Scene} named sceneName.
     */
    public static Scene getScene(String sceneName) {
        SceneManager sm = (SceneManager) SystemManager.get(SceneManager.class);
        Scene s = sm.getScene(sceneName);
        return s;
    }

    /**
     * Retrieve the current active Scene from the scene manager
     *
     * @return the current active {@link Scene}.
     */
    public static Scene getCurrentScene() {
        Scene scene = ((SceneManager) SystemManager.get(SceneManager.class)).getCurrent();
        return scene;
    }

    /**
     * Add a Scene <code>ts</code> named <code>sceneName</code> to the scene manager.
     *
     * @param sceneName Name of the scene to be added to the scene manager
     * @param ts        the corresponding Scene instance to be added
     */
    public static void addScene(String sceneName, Scene ts) {
        ((SceneManager) SystemManager.get(SceneManager.class)).add(sceneName, ts);
    }

    public static void activateScene(String sceneName) {
        ((SceneManager) SystemManager.get(SceneManager.class)).activate(sceneName);
    }

}
