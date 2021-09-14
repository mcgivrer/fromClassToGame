---
title: From a Class to Game chapter: 14 - Collision System author: Frédéric Delorme description: Adding a Camera to the
scene will allow you to move in as you want, and follow a target. created: 2021-09-08 tags: gamedev, camera, target
---

## Collision System

### Introduction

When in a game multiple objects are moving on screen, it often happened there are colliding together.
The `CollisionSystem` intends to provide events on collision, and a mechanism to respond to such event.

To let Collision detection works, there are a bunch of way to encapsulate the colliding objects, and some of the
algorithm are well known: Axis-Aligned Bounding Box, Oriented Bounding Box, and the shape of the bjects can be POINT,
CIRCLE, ELLIPSE or RECTANGLE.

### BoundingBox ?

A bounding box is a box containing the object the engine must manage, and delimiting the edges of this object.
Collission detection between 2 objects is process against the respective bounding box of those objects:

- Here is a rectangular bounding box for a ball:

```text
+--------+
|  ****  |
| ****** |
|********|
|********|
| ****** |
|  ****  |
+--------+
```

- You can also have a circle shape bounding box:

```text
   +--+
  /****\
 /******\
|********|
|********|
 \******/
  \****/
   +--+
```

- Or simply some points in the shape:

```text
    p1
     +**+  p2
    ******
   *******+ p3
p4 +*******
    ******
  p5 +**+ p6
```

The points p1 to p6 are juste tested against the other object. And if one of the point collides, boom !

## Collision !

And detecting collision between Bounding bow will rely on the collision test between those shapes.

### Rectangular bounding box

```text
  (x1,y1)  w1
     O--------+
     |  ****  | shape1
     | ****** |
     |***(x2,y2) w2
  h1 |*****O--------+
     | ****|\ |***  | shape2
     |  ***| \|**** |
     +-----|--o*****| h2
           |********|
           | ****** |
           |  ****  |
           +--------+
```

In this diagram, a shape 1 collide a shape 2. shape 1 at (x1,y1) is sized of (w1,h1) and shape 2 is at (x2,y2) and sized
of (w2,h2).

- O(h1,y1) is the shape 1 position
- O(h2,y2) is the shape 2 position
- o(x1+w1,y1+h1) is the right bottom corner of the shape 1

The collision penetration vector is (Oo).

The response will be defined accordingly to the shpae 1 and shape 2 velocity and mass.

### Circular bounding box

For the case where bounding boxes are CIRCLEs, and the shapes position are centre aligned :

```text
     d1
   +-^-+
  /**|**\ shape1
 /***|***\
|****O(x1,y1)
 \****\+---+
  \***/\****\
   +-/--\****\
    |****O(x2,y2)
     \***|***/
      \**|**/ shape2
       +-v-+
         d2
```

The collision detection is more simple :

if the distance between O(x1,y1) and O(x2,y2) is less than (d1 + d2), shape1 and shape2 are colliding !

### Rectangles at center

You can also aligned your rectangular bounding box to the shapes center:

```text
          w1
     +---------+
     |  *****  | shape1
     | ******* |
     |****O(x1,y1)
  h1 |******+--|------+
     | *****|  |****  | shape2
     |  ****| ******* |
     +------|-*+*O(x2,y2)        
            | ******* |
            |  *****  | h2
            +--------+
                w2
```

In this case, the collison detection is performed in the half height and half width of the shapes.
