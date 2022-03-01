package fr.snapgames.fromclasstogame.core.behaviors;

import fr.snapgames.fromclasstogame.core.gfx.Render;
import fr.snapgames.fromclasstogame.core.io.actions.ActionHandler;

/**
 * <p>The Behavior interface is a hook on any entity to implement some specific processing on some event.
 * example:</p>
 * <ul>
 *     <li>Implementation is input processing to manage an entity,</li>
 *     <li>Particle animation,</li>
 *     <li>collision response processing,</li>
 *     <li>Inventory management.</li>
 * </ul>
 *
 * @param <T> the Entity onto the Behavior must be applied.
 * @author Frédéric Delorme
 * @since 1.0.2
 */
public interface Behavior<T> {

    /**
     * Processing triggered on T entity creation operation
     *
     * @param entity the entity T to be linked to this behavior
     */
    default void onCreate(T entity) {
        // Nothing specific to do by default.
    }

    /**
     * Processing on the input event for this T entity
     *
     * @param entity the entity T to be linked to this behavior
     * @param ah     the ActionHandler
     */
    default void onInput(T entity, ActionHandler ah) {
        // Nothing specific to do by default.
    }

    /**
     * Processing on the Update operation for the T entity.
     *
     * @param entity  the entity T to be linked to this behavior
     * @param elapsed elapsed time since previous call
     */
    default void onUpdate(T entity, long elapsed) {
        // Nothing specific to do by default.
    }

    /**
     * Processing with render operation for the T entity.
     *
     * @param go     the entity T to be linked to this behavior
     * @param render the render service to delegate drawing operation to.
     */
    default void onRender(T go, Render render) {
        // Nothing specific to do by default.
    }

    /**
     * Processing any Action for this T entity.
     *
     * @param entity the entity T to be linked to this behavior
     * @param action the action to be processed.
     */
    default void onAction(T entity, Integer action) {
        // Nothing specific to do by default.
    }
}
