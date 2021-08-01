---
title: FromClass to Game
chapter: 06 - New Components
author: Frédéric Delorme
description: Adding some new components to the game framework.
created: 2021-08-01
tags: gamedev, text, resources
---

## New Components

### A Text at display

#### TextObject class

The `TextObject` will be used to display text on screen. Starting from a standard `GameObject`, we just have to add 2 more properties:

- font : to define which standard font we want to use to display text (see [java.awt.Font](https://docs.oracle.com/javase/8/docs/api/java/awt/Font.html) for details).
- text : simply s String containing the text to be displayed.

As we already done for the GameObject, we will add as Fluent API a `setFont(Font)` and a `setText(String)` to the existing one.

```java
public class TextObect extends GameObject {
    public Font font;
    public String text;

    public TextObject(String name, double x, double y){
        super(name, x, y);
    }

    public TextObject setFont(Font font){
        this.font=font;
        return this;
    }
    public TextObject setText(String text){
        this.text=text;
        return this;
    }
}
```

#### Render adaptation

The `Render.draw(Graphics,GameObject)` must be modified to let it render the `TextObject`. In a first run, we will use a simple if to capture the nature of the object to be rendered:

```java
    private void draw(Graphics2D g, GameObject go) {
        String goClazzName = go.getClass().getName();

        // rendering the already existing GameObject
        if (GameObject.class.getName().equals(goClazzName)) {

        // rendering the new TextObject
        } else if (TextObject.class.getName().equals(goClazzName)) {
            TextObject to = (TextObject)go;
            g.setColor(to.color);
            g.setFont(to.font);
            g.drawString(to.text, (int)(to.x), (int)(to.y));

        }
    }
```

#### How to use it ?

You just have to create a Text Object and add is to the Game objects list:

```java
    InputStream is = Game.class.getClassLoader().getResourceAsStream("fonts/FreePixel.ttf");
    Font textFont = Font.createFont(Font.TRUETYPE_FONT, is);

    TextObject text = new TextObject("text", 10, 10)
            .setText("Sample from class to game")
            .setFont(textFont);
    add(text);

```

### Resource Manager

As we now need more that just images to be displayed in our game, we need some other kind of resources like font, sound and so on, we need to add a ResourceManager which work consist in managing all those kind of resources.

(TODO)

And that's It !
