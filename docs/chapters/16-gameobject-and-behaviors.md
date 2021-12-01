# GameObject and Behaviors

A GameObject can share some of their behaviors.

To be able to share such code, we need to create a new interface named Behavior, definig some of the possible
interaction between GameObject and the rest of the world.

```java
public interface Behavior {
    void input(InputHandler ih);

    void update(GameObject go, long dt);

    void render(GameObject go, Render r);
}
```

This simple interface can let add new behavior to a GameObject by adding an implementation to the object:

```java
public class GameObject {
    private List<Behavior> behaviors = new ArrayList<>();

    public GameObject add(Behavior b) {
        assert (behaviors.contains(b));
        behaviors.add(b);
        return this;
    }
}
```

Then at Game engine level, we will need to process the corresponding implementation's input, update and render call.

## The Player Action Behavior

As a player, I must be able to interact with the Game, and move my character on screen.

This is what we are going to try and achieve by implementing the `PlayerActionBehavior`.

Start implementing this behavior :

```java
public class PlayerActionBehavior implements Behavior<GameObject> {
    public PlayerActionBehavior() {
        ah = (ActionHandler) SystemManager.get(ActionHandler.class);
    }
}
```

We need to focus on what we want to be able to achieve as move:

- move LEFT,
- move RIGHT,
- make the character JUMP,
- RESET every velocity and acceleration parameters (only for debug purpose)

So we will capture the `onInput()` method to catch left and right key press:

```java
public class PlayerActionBehavior implements Behavior<GameObject> {

    //...
    @Override
    public void onInput(GameObject go, ActionHandler ih) {
        if (ih.get(KeyEvent.VK_LEFT)) {
            go.acceleration.x = -accel;
        }
        if (ih.get(KeyEvent.VK_RIGHT)) {
            go.acceleration.x = accel;
        }
    }
    
}
```

but we rely on the `onAction()` to detect JUMP and RESET parameter.

```java
public class PlayerActionBehavior implements Behavior<GameObject> {

    //...
    @Override
    public void onAction(GameObject go, ActionHandler.ACTIONS action) {
        accelStep = (Double) go.getAttribute("accelStep");
        jumpAccel = (Double) go.getAttribute("jumpAccel");
        jumping = (boolean) go.getAttribute("jumping");

        if (ah.getCtrl()) {
            accel = accelStep * 10;
        } else if (ah.getShift()) {
            accel = accelStep * 5;
        } else {
            accel = accelStep;
        }
        switch (action) {
            case UP:
                jumping = (boolean) go.getAttribute("jumping");
                if (!jumping) {
                    go.acceleration.y = jumpAccel * accel;
                    go.addAttribute("jumping", true);
                }
                break;
            case FIRE1:
                if (go.debug > 0) {
                    go.acceleration.x = 0;
                    go.acceleration.y = 0;
                    go.velocity.x = 0;
                    go.velocity.y = 0;
                }
                break;
            default:
                break;
        }
    }
}
```