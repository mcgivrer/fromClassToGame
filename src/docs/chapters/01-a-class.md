# *A Class ?

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
        System.out.println(
            String.format(
                "Arguments on start : [%s]", 
                argv.toString()));
    }


    public static void main(String[] argv) {
        Game mc = new Game("This is my Game", 320, 200);
        mc.run(argv);
    }

}
```

Here is a very basic java class to start with. Let's go !
