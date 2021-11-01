# Enhancing Behavior

The existing Behavior we've just created is dedicated to the GameObject. But, regarding the Scene implementation, we
have some common needs between both, like managing, but at Scene level, some player or user input, like debug
configuration or else, and the Behavior interface would be very interesting to be used here.

Thanks to this interface we would be able to implement input, update or render specific requirement.

So let's adapt the Behavior interface to this new need.

## The parameterized Behavior

So now the Behavior is dedicated to the GameObject, so the received object to apply the required processing is a
GameObject.

Changing this will simply consist in replacing GameObject in the interface by a T parameter:

```java
public interface Behavior<T> {
    void onInput(T go, ActionHandler ih);

    void onUpdate(T go, long dt);

    void onRender(T go, Render r);

    void onAction(T go, ActionHandler.ACTIONS action);
}
```

So creating a SceneBehavior will be :

```java
public class MySceneBehavior implements Behavior<Scene> {
    void onInput(T go, ActionHandler ih) {
        if (ih.get(KeyEvent.VK_D)) {
            // do fancy thing to manage Debug display.
        }
    }

    void onUpdate(T go, long dt) {
    }

    void onRender(T go, Render r) {
    }

    void onAction(T go, ActionHandler.ACTIONS action) {
    }
}
```

Here is the opportunity to capture the `d` key and activate some incredible stuff.

But to activate this scene behavior, the SceneManager must be adapted and the Game class modified.

