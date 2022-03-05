# A Class, begin of everything

A class is a pure OOP entity, defining an object, with its own attributes, which can be named characteristics, and its
own methods, that we can name actions.

The very basic one in the Java language, and offering the opportunity to be run, consists of the following lines.

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
        System.out.println("Start game " + this.title);
        System.out.println("- window " + this.width + "x" + this.height);
        System.out.println("Arguments on start :");
        java.util.Arrays.stream(argv).foreach(s -> {
            System.out.println("- " + s);
        });
        System.out.println("end game");
    }


    public static void main(String[] argv) {
        Game mc = new Game("This is my Game", 320, 200);
        mc.run(argv);
    }

}
```

Here is a very basic java class to start with.

- The main method is the java entry point.
- The run will start the game.

If you are curious, you can already build he project with the following command:

```bash
mvn clean install
```

And see in the `/target` directory a new `*-shaded.jar` appeared.

You can run it :

```bash
java -jar target/fromclasstogame-0.0.1-SNAPSHOT-shaded.jar
```

You will observe in the terminal window some output listing the arguments passed at start:

```bash
Start game This is my Game
- window 320x200
Arguments on start:
- ...
end game
```

We will go in more details on the next chapter.

