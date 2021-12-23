package fr.snapgames.fromclasstogame.core.scenes.transition;

import fr.snapgames.fromclasstogame.core.gfx.Render;

/**
 * <p>the {@link Transition} Interface to be specialized is defining a transition processing and rendering for a
 * T object type.</p>
 * <p>This transition will be called during the rendering operation (see {@link Render}) and start and end T object
 * can be updated by the {@link fr.snapgames.fromclasstogame.core.physic.PhysicEngine}.</p>
 * <p>
 * The Transition<T> will be initialized at its creation, and then, the {@link Transition#start(Object, long)} operation
 * is  called. During the transition's life, the {@link Transition#update(Object, long)} and
 * {@link Transition#render(Object, Render)} methods are called.</p>
 * <p>
 * When the end of the transition is reach (duration=0), the end() operation is called.</p>
 *
 * @param <T> the type of object to apply the transition to.
 */
public interface Transition<S, T> {

    /**
     * Called at Start of transition effect.
     *
     * @param start the first object to be modified by transition
     * @param dt    time of start operation
     */
    void start(T start, long dt);

    /**
     * Called at end of transition effect.
     *
     * @param dt time of end operation
     */
    void end(long dt);

    /**
     * Update from <code>object1</code> to <code>object2</code> according to elapsed time dt.
     *
     * @param end the second object to be modified by transition
     * @param dt  the elapsed time since previous call.
     */
    void update(T end, long dt);

    /**
     * Render the transition on the object delegating operation to <code>render</code>.
     *
     * @param end    the second object to be rendered by transition
     * @param render The {@link Render} object to delegate rendering operation to.
     */
    void render(T end, Render render);

    /**
     * The processing to be implemented to compute the transition from <code>start</code> T and <code>end</code> T}
     *
     * @param system the {@link System} to be used for processing the T object.
     * @param start  the starting T object
     * @param end    the ending T object
     */
    void process(S system, T start, T end);

    /**
     * return true if transition is active;
     *
     * @return
     */
    boolean isActive();
}
