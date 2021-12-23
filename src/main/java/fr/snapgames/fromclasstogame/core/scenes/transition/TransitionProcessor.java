package fr.snapgames.fromclasstogame.core.scenes.transition;

/**
 * This transition processor will call a {@link Transition#process(S, Object, Object)} methods to performs the
 * transition from start to end T object.
 *
 * @param <T>
 */
public class TransitionProcessor<S, T> {

    private T start;
    private T end;


    TransitionProcessor() {

    }

    /**
     * Define the starting point for the transition.
     *
     * @param start
     */
    public void set(T start, T end) {
        this.start = start;
        this.end = end;
    }


    public void update(Transition<S, T> tr, long dt) {
        tr.update(end, dt);
    }
}
