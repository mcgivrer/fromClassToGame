---
title: FromClass to Game
chapter: 02 - Adding a Structure
author: Frédéric Delorme
description: Moving from a simple class to a game structure.
created: 2021-08-01
tags: gamedev, structure, game, loop
---

## Adding a Structure

### Core Game methods

So now we had a basic class for our game, we need to ass some game structure.

```java
public class Game{
    ...
    // Entry point for the game.
    public void run(Strng[] argv){
        initialiaze(argv);
        loop();
        dispose();
    }

    private void initialize(String[] argv){

    }

    private void loop(){

    }
}
```

Our game will start with a main entry point: the run(argv) method.

This main method will delegate all the sub rpocessing to other methods:

- `initialize(argv)` will get cli parameters to set new values for title, width and height of the game window. more to come in the next chapters.
- `loop()` is the core processing for our game, this where all will happened.

### The LOOP

The `loop` method is where player input will be processed and where the game and all its internal objects and entities will be updated accordingly, and finally all those objects will be displayed, if necessary.

```java
public class Game{
    ...
    // the flag requestin gthe game to exit.
    private boolean exit = false;

    private initialize(String[] argv){}

    private void loop(){
    long start = System.currentTimeMillis();
    long previous = start;
    long dt = 0;
    while (!exit) {
      start = System.currentTimeMillis();
      dt = start - previous;
      input();
      update(dt);
      draw();
      previous = start;
    }    }

    private void input(){}
    private void update(long dt){}
    private void draw(){}
}
```

So we will need the corresponding methods in our class:

- `input()` will process user input (the player),
- `update(dt)` will compute position and behavior os objects in the game,
- and `draw()` will display all the neede object on the game window.

You've also certainly noticed the boolean `exit`, used to exit the main loop on user or game request. By default the boolean is `false`, and to request exiting, just set it `true`.

Another thing you probably noticed is the `dt` computation, passed to the update(dt) method. the dt value is the elapsed time since previous update call. this will be used to compute some objects moves, taking in the time in math formula.

But before going further, we need a Game Windows !
So let's add a JFrame object to create a window and capture keyboard events:

```java
public class Game{
    ...
    // the flag requesting the game to exit.
    private boolean exit = false;
    // the game window
    private JFrame frame;
    ...

    private void initialize(String[] argv){

        parseArgs(argv);
        // define the JFrame window title
        frame = new JFrame(this.title);

        // please, close the window on exit request
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // defin ethe window Size
        Dimension d = new Dimension(this.width,this.height);
        frame.setPreferredSize(d);
        frame.setMaxSize(d);
        frame.setSize(d);
        frame.pack();
        // let show the window !
        frame.setVisible(true);
    }

}
```

#### input

We will need to capture the keyboard events to be able at least to interact with the player.

#### update

just update the object to be displayed:

#### draw

and finally draw things.
