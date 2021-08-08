---
title: From a Class to Game 
chapter: 01 - A Class ?
author: Frédéric Delorme
description: Creating a first class as a Game container. 
created: 2021-08-01 
tags: gamedev, staring point
---

## A Class ?

A class is a pure OOP entity, defining an object, with its own attributes, which can be named characteristics, and its
own methods, that we can name actions.

```java
public class Game {
    private int id;
    private String title;
    private int width, height;

    public Game(String title, int w, int h) {
        this.title = title;
        this.width = w;
        this.height = h;
    }

    public void run(String[] argv) {
        System.out.println(String.format("Arguments on start : [%s]", argv.toString()));
    }


    public static void main(String[] argv) {
        Game mc = new Game("This is my Game", 320, 200);
        mc.run(argv);
    }

}
```

Here is a very basic java class to start with. Let's go !
