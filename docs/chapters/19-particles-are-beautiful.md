# Particles are beautiful

## What is a particle ?

A particle is, in the gamedev world, a small graphical element, used in a group of the same element to create special
effects like smoke, fog, rain, snow and fire. (those are only some examples, there so many other way to use particles.)

Particle animation will be performed by assigning a bunch of Particles with the same behavior. This bunch of Particle is
managed into a ParticleSystem. 

Specific animation will be delegated to a Behavior. And particle rendering will be delegated to a specific Render Helper.

So, playing with component and behavior,you will be able to animate particles according to your needs.

## A Particle !

The first class we will need to create is the Particle:

```java
public class Particle {
    Vector2d position;
    Vector2d velocity;
    Vector2d acceleration;
    long life;
    boolean alive;
    Color color;
    double size;
}
```

Here are our first attributes:

- `position`, `velocity` and `acceleration` will be used to compute particle position using some basic physic mathematics.
- `life` and `alive` are processed to define particle age and the duration of the particle animation.
- and finally, `color` and `size` are used for rendering purpose.

Ok, now we have defined our Particle class, we need to manage them into a already known way in our framework.

let's delegate this to the `ParticleSystem` class.

## ParticleSystem

