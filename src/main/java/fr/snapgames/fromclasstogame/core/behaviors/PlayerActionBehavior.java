package fr.snapgames.fromclasstogame.core.behaviors;

import java.awt.event.KeyEvent;

import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.gfx.Render;
import fr.snapgames.fromclasstogame.core.io.ActionHandler;
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
    public void onInput(GameObject go, ActionHandler ih) {
        if (ih.get(KeyEvent.VK_LEFT)) {
            go.forces.add(new Vector2d(-accel, 0.0));
        }
        if (ih.get(KeyEvent.VK_RIGHT)) {
            go.forces.add(new Vector2d(accel, 0.0));
        }
    }

    @Override
    public void onUpdate(GameObject go, long dt) {

    }

    @Override
    public void onRender(GameObject go, Render r) {

    }

    @Override
    public void onAction(GameObject go, ActionHandler.ACTIONS action) {
        accelStep = (Double) go.getAttribute("accelStep", 0.0);
        jumpAccel = (Double) go.getAttribute("jumpAccel", 0.0);
        jumping = (boolean) go.getAttribute("jumping", false);

        if (ah.getCtrl()) {
            accel = accelStep * 10;
        } else if (ah.getShift()) {
            accel = accelStep * 5;
        } else {
            accel = accelStep;
        }
        switch (action) {
            case UP:
                jumping = (boolean) go.getAttribute("jumping", false);
                if (!jumping) {
                    go.forces.add(new Vector2d(0.0, jumpAccel * accel));
                    go.addAttribute("jumping", true);
                }
                break;
            case FIRE1:
                go.forces.clear();
                go.acceleration.x = 0;
                go.acceleration.y = 0;
                go.velocity.x = 0;
                go.velocity.y = 0;
                break;
            default:
                break;
        }
    }
}
