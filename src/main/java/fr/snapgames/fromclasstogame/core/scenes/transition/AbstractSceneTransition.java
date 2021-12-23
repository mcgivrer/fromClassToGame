package fr.snapgames.fromclasstogame.core.scenes.transition;

import fr.snapgames.fromclasstogame.core.gfx.Render;
import fr.snapgames.fromclasstogame.core.scenes.Scene;

public abstract class AbstractSceneTransition implements Transition<Render, Scene> {
    private long timeStart;
    long duration;
    private long time;
    private Scene startScene;

    public AbstractSceneTransition() {
    }


    @Override
    public void start(Scene start, long dt) {
        this.startScene = start;
        this.timeStart = dt;
    }

    @Override
    public void end(long dt) {
    }

    @Override
    public void update(Scene end, long dt) {
        if (isActive()) {
            time += dt;
        }
    }

    /**
     * this methd is called by the SceneManager at rendering time.
     *
     * @param end    the second object to be rendered by transition
     * @param render The {@link Render} object to delegate rendering operation to.
     */
    @Override
    public void render(Scene end, Render render) {
        process(render, startScene, end);
    }

    abstract public void process(Render render, Scene start, Scene end);

    public boolean isActive() {
        return time <= duration;
    }

    public long getTime() {
        return this.time;
    }
}
