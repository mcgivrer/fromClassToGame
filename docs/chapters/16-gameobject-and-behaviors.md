---
title: From a Class to Game
chapter: 16 - Gameobject and Behaviors
author: Frédéric Delorme
description: The GameObject's behaviors ar sometimes the same between Object. the Behavior interface will provide a way \
to share common behaviors between multiple GameObject instances.
created: 2021-10-18
tags: gamedev, gameobject, behavior
---

## GameObject and Behaviors

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

