package fr.snapgames.fromclasstogame.core.behaviors;

import java.awt.event.KeyEvent;

import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.io.actions.ActionHandler;
import fr.snapgames.fromclasstogame.core.physic.Vector2d;
import fr.snapgames.fromclasstogame.core.system.SystemManager;

/**
 * The {@link PlayerActionBehavior} requires some GameObject attributes:
 * <ul>
 * <li><code>accelStep</code> the value for the acceleration factor to be
 * applied by default in the object on any directional move</li>
 * <li><code>jumpAccel</code> the vertical acceleration to be applied in case of
 * jumping action</li>
 * <li><code>jumping</code> a boolean flag storing the current jump action: true
 * = jumping</li>
 * <li><code>touching</code> a boolean flag storing the current contact action:
 * true = contact</li>
 * </ul>
 * <p>
 * Usage :
 *
 * <pre>
 * GameObject player = new GameObject("myPlayerObject", new Vector2d(160, 100))
 *     .addAttribute("jumping", false)
 *     .addAttribute("touching", false)
 *     .addAttribute("accelStep", 20.0)
 *     .addAttribute("jumpAccel", -40.0);
 *     .add(new PlayerActionBehavior());
 * </pre>
 */
public class PlayerActionBehavior implements Behavior<GameObject> {

    private double accelStep;
    private double jumpAccel;
    private boolean jumping;
    private boolean touching;
    double accel = 0.0;
    private ActionHandler ah;

    public PlayerActionBehavior() {
        ah = (ActionHandler) SystemManager.get(ActionHandler.class);
    }

    @Override
    public void onInput(GameObject entity, ActionHandler ah) {
        if (ah.get(KeyEvent.VK_LEFT)) {
            entity.forces.add(new Vector2d(-accel, 0.0));
        }
        if (ah.get(KeyEvent.VK_RIGHT)) {
            entity.forces.add(new Vector2d(accel, 0.0));
        }
    }

    @Override
    public void onAction(GameObject entity, Integer action) {
        accelStep = (Double) entity.getAttribute("accelStep", 2.0);
        jumpAccel = (Double) entity.getAttribute("jumpAccel", 0.0);
        jumping = (boolean) entity.getAttribute("jumping", 0.0);

        if (ah.getCtrl()) {
            accel = accelStep * 10.0;
        } else if (ah.getShift()) {
            accel = accelStep * 5.0;
        } else {
            accel = accelStep;
        }

        switch (action) {
            case ActionHandler.UP:
                jumping = (boolean) entity.getAttribute("jumping", false);
                if (!jumping) {
                    entity.forces.add(new Vector2d(0.0, jumpAccel * accel));
                    entity.addAttribute("jumping", true);
                }
                break;
            case ActionHandler.FIRE1:
                entity.forces.clear();
                entity.acceleration.reset();
                entity.velocity.reset();
                break;

            case ActionHandler.LEFT:
                entity.acceleration.x = -accel;
                break;
            case ActionHandler.RIGHT:
                entity.acceleration.x = accel;
                break;

            default:
                break;
        }
    }
}
