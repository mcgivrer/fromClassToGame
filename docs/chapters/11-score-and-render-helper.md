# Score and RenderHelper

In the previous chapters, the demo scene was displaying some `GameObject`'s and a `TextObject` as a score display. In
this chapter, we are going to create a new `ScoreObject` to support all the score mechanism. But if we want to render
this object, we would have to modify the `Render` class to support this new rendering.

And if we think further than just this score new display entity, each time we will have to add a new enttiy in the game,
we would have to extend or modify the `Render` class.

This approach is just not possible if our goal consists in providing a kind of game framework.

So let's refactor the `Render` class to support new mechanism, called delegation, to let it draw all specific object
with a dedicated utility: a Helper.

### Modifying the Render pipeline

First, to let the Render manage all the kind of GameObject, let's introduce an `Entity` interface:

```java
public interface Entity {
}
```

You can see that this entity is just an empty interface.

Now, let's see how `GameObject` and `TextObject` instances are identified and displayed from the `Renderer` class.

```java
class Renderer {
    //...
    private void draw(Graphics2D g, GameObject go) {
        g.setColor(go.color);
        String goClazzName = go.getClass().getName();
        if (GameObject.class.getName().equals(goClazzName)) {
            if (go.image != null) {
                g.drawImage(go.image, (int) (go.x), (int) (go.y), null);
            } else {
                g.drawRect((int) (go.x), (int) (go.y), (int) (go.width), (int) (go.height));
            }
        } else if (TextObject.class.getName().equals(goClazzName)) {
            TextObject to = (TextObject) go;
            g.setFont(to.font);
            g.drawString(to.text, (int) (to.x), (int) (to.y));
        }
    }
    //...
}
```

We badly test the class of the object and process as required for each type.

The Helper will provide the necessary operation to draw each of those object.

- for a `GameObject`:

```java
class Renderer {
    //...
    private void draw(Graphics2D g, GameObject go) {
        //...
        if (go.image != null) {
            g.drawImage(go.image, (int) (go.x), (int) (go.y), null);
        } else {
            g.drawRect((int) (go.x), (int) (go.y), (int) (go.width), (int) (go.height));
        }
        //...
    }
    //...
}
```

- for a `TextObject`:

```java 
g.setFont(to.font); 
g.drawString(to.text, (int) (to.x), (int) (to.y));
```

If we want to keep the same mechanism, but adaptable: we would need a specific Helper interface, named RenderHelper to
let explain which can be drawn with which operation:

```java
public interface RenderHelper {
    String getType();

    void draw(Graphics2D g, Object go);
}
```

- the `getType()` will return the name of the class to be drawn,
- the `draw()` method will proceed the object drawing operation.

So for a GameObject :

```java
public class GameObjectRenderHelper implements RenderHelper {

    @Override
    public void draw(Graphics2D g, Object o) {
        GameObject go = (GameObject) o;

        if (go.image != null) {
            g.drawImage(go.image, (int) (go.x), (int) (go.y), null);
        } else {
            g.drawRect((int) (go.x), (int) (go.y), (int) (go.width), (int) (go.height));
        }

    }

    @Override
    public String getType() {
        return GameObject.class.getName();
    }

}
```

New we have a code sample for a RenderHelper, let's modify the Render to be able to use those helpers.

```java
public class Renderer {
    //...
    private Map<String, RenderHelper> renderHelpers = new HashMap<>();
    //...

    private void draw(Graphics2D g, GameObject go) {
        String goClazzName = go.getClass().getName();
        if (renderHelpers.containsKey(goClazzName)) {
            RenderHelper rh = renderHelpers.get(goClazzName);
            rh.draw(g, go);
        } else {
            g.setColor(go.color);
            g.drawRect((int) (go.x), (int) (go.y), (int) (go.width), (int) (go.height));
        }
    }
    //...

    public void addRenderHelper(RenderHelper rh) {
        renderHelpers.put(rh.getType(), rh);
    }
}
```

So first we add a Map `renderHelpers` to support and contain all configured `RenderHelper`. creating
an `addRenderHelper()` method to add new `RenderHelper` to the `renderHelpers`. Then, we use the `RenderHelper#draw()`
all of those objects, delegating the draw operation to the right `RenderHelper` on class name, using
the `RenderHelper#getType()` from implementation.

And finally, need to declare default RenderHelper into the Render constructor:

```java
class Renderer {
    //...
    public Renderer(int width, int height) {
        setViewport(width, height);
        addRenderHelper(new GameObjectRenderHelper());
        addRenderHelper(new TextRenderHelper());
    }
    //...
}
```

### Adding a ScoreObject

Let's create a brand new `Entity` : the `ScoreObject` in charge of supporting and rendering the new score information on
the `DemoScene`.

The `ScoreObject` is nothing mire than a `TextObject` having a specific internal attribute containing the score value.

```java
public class ScoreObject extends TextObject {
    private int score;

    public ScoreObject(String name, double x, double y) {
        super(name, x, y);
        score = 0;
    }

    public ScoreObject setScore(int s) {
        score = s;
        text = String.format("%06d", score);
        return this;
    }
}
```

We just need a setter to set the internal rendered text from the inherited `TextObject` according to the score value.
Using a String format trick to populate the text attribute, the score is now rendy to be rendered, with a
new `ScoreRenderHelper` :

```java
public class ScoreRenderHelper implements
        RenderHelper {
    @Override
    public void draw(Graphics2D g, Object o) {
        ScoreObject so = (ScoreObject) o;
        g.setFont(so.font);
        g.drawString(so.text, (int) (so.x), (int) (so.y));
    }

    @Override
    public String getType() {
        return ScoreObject.class.getName();
    }
}

```

So first the `getType()` will return the concerned Entity type concerned by the `RenderHelper`, and the `draw()` will
perform the rendering of this new `ScoreObject`.

### Adapt the TextObject rendering

We will have to erform the same thing for the `TextObject` :

```java
public class TextRenderHelper implements RenderHelper {
    @Override
    public void draw(Graphics2D g, Object go) {
        TextObject to = (TextObject) go;
        g.setFont(to.font);
        g.drawString(to.text, (int) (to.x), (int) (to.y));
    }

    @Override
    public String getType() {
        return TextObject.class.getName();
    }
}
```

And that's good now for the `ScoreObject` and its rendering helper.

### the DemoScene

The  `DemoScene` must also be adapted to declare the new `ScoreRenderHelper`; we will do this during
the `Scene#initialize()` phase :

```java
class DemoScene {
    //...
    @Override
    public void initialize(Game g) {
        super.initialize(g);
        // Add a specific Render for the new ScoreObject
        g.getRender().addRenderHelper(new ScoreRenderHelper());
        //...
    }
    //...
}
```

And now let's run this new masterpiece of code !

