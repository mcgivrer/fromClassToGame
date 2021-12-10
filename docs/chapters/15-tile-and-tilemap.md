# Tiles and TileMap

In any platform game, we raid through multiple worlds where each levels describe a part of the adventure. Those levels
are sometimes vast, or small, long or short, but each time there are decors and enemies and traps. Each level, if you've
not already noticed it, is composed of multiple little graphic tiles. And those small tiles are also sometimes reused
from level to level: a door, an object, a wall, a dangerous chest ...

So, if you want to code some platform game, you need a technology to display such levels.

Here is where w are going to talk about `Tile`, `TileSet`, `TileLayer` and `TileMap`.

## What is a Tile ?

So the basic graphic element we need to use in our worlds levels are tiles.

A Tile is a small graphic element with attributes describing the nature of the tile, its graphics part, and if it is
animated. Yes, if you want to see beautiful coin swinging on itself in the level, w<e need a way to animate it.

A Tile is:

- a code to identify it,
- a size (width x height)
- a graphic part (image) or animation
- some valuable attributes to define it: blocking, killing, moneying, healthing, etc...

for example:,

1. a coin would be:

- code: `'c'`
- size: `16x16`
- a BufferedImage of 16x128 with 8 frames
- attributes: `[type='money',value = 10]`

2. a simple wall would be :

- code: `'w'`
- size: `16x16`
- a BufferedImage of 16x16
- attributes: `[type='block']`

3. an ice floor :

- code: `'i'`
- size: `16x16`
- a BufferedImage of 16x128 with 8 frames (animated reflecting ice)
- frames: `[300,200,200,100,100,200,200,300]`
- attributes [^1]: `[type='block', friction=0.998]`

[1^]: Here are described the frames and their time `frames` in ms.

### the Tile class

here is the following class used in our level data to define a `Tile`:

```java
import java.awt.image.BufferedImage;

public class Tile {
    Char code;
    int width;
    int height;
    Map<String, Object> Attributes;
    BufferedImage image;
    List<Integer> frames;
}
```

In the level properties file, a `Tile` will be described through a `TileSet` (a set of tiles).

