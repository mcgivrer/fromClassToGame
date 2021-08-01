---
title: From a Class to Game
chapter: 04 - The Game Object
author: Frédéric Delorme
description: Implementing a simple Object to manage them'all.
created: 2021-08-01
tags: gamedev, gameobject
---

## The Object Game

The GameObject entity will be used by every element in the gae to be renderered, interct with and updated.
This is the how-to manage thing into your game.

### Physics

Some basic information must be providied to let an object managed by the game; position, speed, and size are the basical ones.

```java
public class GameObject {
    public double x;
    public double y;
    public double dx;
    public double dy;

    public double width;
    public double height;
}
```

Those attributes are the minimum ones to move any object on a 2D space.

- `x,y` are double values to define 2D space position,
- `dx,dy` are double defining the speed for this object,
- `width,height` are setting the max object size.

But as developers are humans, they need more usable identifiers to debug and manage those objects. But the machine needs also some internal identifiers.

Let's now add some useful things:

```java
public class GameObject {

    private static int index = 0;

    public int id = ++index;
    public String name = "noname_" + id;

    public double x;
    public double y;
    public double dx;
    public double dy;

    public double width;
    public double height;
}
```

The `id` is a unique identifier for each `GameObject` instance. The static `index` is the internal counter used to create the `id` value.
The `name` is defaulty initialized with a `noname_xx` value, where xx is the id value.

And finally as we also need to display those object, and this is mainly the main goal, you need to get `color` for rendering little things. The `priority` attribute will be used to sort all the object befor renderering, managing a depth level between object at rendering time.
The `image` will be used to render an... image with a `BufferedImage` :)
If this `image` attributes is null, a rectangle of size `width x height` will be rendered (see the chapter 03 at `Render` class).

```java
public class GameObject {

    private static int index = 0;

    public int id = ++index;
    public String name = "noname_" + id;

    public double x;
    public double y;
    public double dx;
    public double dy;

    public double width;
    public double height;

    public Color color;
    public int priority;
    public BufferedImage image;
}
```

Ok now that we know what to render, we need to add some moves.

```java
public void update(long dt) {
    x += dx * dt;
    y += dy * dt;
}
```

This is a code corresponding formula to compute position depending on its speed:

```Math
p1 = p0 + v0*t
```

Some other methods are added vor some convience to define easily some properties.

OK, now we will need to update :

- The `Game` class to support a bunch of `GameObject`'s,
- the `Render` class to let it draw the `GameObject`.

### The Game

```java
public class Game {
    ...
    Map<String, GameObject> objects = new HashMap<>();
    ...

    public void add(GameObject go){
        if(!objects.containsKey(go.name)){
            objects.put(go.name, go);
            objectsList.add(go);
            renderer.add(go)
        }
    }

    ...

      /**
   * Update all the game mechanism
   */
    private void update(long dt) {
        for(GameObject e:objectsList){
            e.update(dt);
        }
    }
}
```

A new `add(GameObject)` method will add the GameObject to the Game objects list and a map. The list and the Map are shorcut to easily manage the object into the Game.

The update method has been modified to update all the objects.

The `update(long)` method will perform a call to all the `GameObject.update(long)` method.

### Render

The Render class is now to be updated to draw all those objects.

Let's add some methods.

We need to maintain a list of object to be rendered, and a methd to add one.

```java
public class Render {
    ...
    public Render add(GameObject go) {
        if (!objects.contains(go)) {
            objects.add(go);
            objects.sort((a, b) -> {
                return a.priority < b.priority ? -1 : 1;
            });
        }
        return this;
    }
    ...
    private void draw(Graphics2D g, GameObject go) {
        g.setColor(go.color);
        if (go.image != null) {
            g.drawImage(go.image, (int) (go.x), (int) (go.y), null);
        } else {
            g.drawRect((int) (go.x), (int) (go.y), (int) (go.width), (int) (go.height));
        }
    }
}
```

The `add()` will add a GameObject to the "rendering list", and the `draw()` method will render a the corresponding image (if not null) or draw a rectangle.

So now, calling the `render()` method will parse in the priority sort order all the `GameObject` in the objects list, and draw each of those items.
