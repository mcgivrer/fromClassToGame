# Particles are beautiful

## What is a particle ?

A particle is, in the gamedev world, a small graphical element, used in a group of the same element to create special
effects like smoke, fog, rain, snow and fire. (those are only some examples, there so many other way to use particles.)

Particle animation will be performed by assigning a bunch of Particles with the same behavior. This bunch of Particle is
managed into a ParticleSystem.

Specific animation will be delegated to a Behavior. And particle rendering will be delegated to a specific Render
Helper.

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

- `position`, `velocity` and `acceleration` will be used to compute particle position using some basic physic
  mathematics.
- `life` and `alive` are processed to define particle age and the duration of the particle animation.
- and finally, `color` and `size` are used for rendering purpose.

Ok, now we have defined our Particle class, we need to manage them into a already known way in our framework.

Let's delegate this to the `ParticleSystem` class.

## ParticleSystem

The ParticleSystem object must be managed as all other GameObject, so the easy way consists in inheriting from
GameObject to create our new ParticleSystem.

This new class has a master attribute : an array of Particle, to apply some common animation mechanism of those
particles from the GameObject update method.

```java
public class ParticleSystem extends GameObject {
    private List<Particle> particles;
}
```

The second important attribute is the list of behavior to be applied on each of those particles:

```java
public class ParticleSystem extends GameObject {
    private List<Behavior<Particle>> pBehaviors;
    private List<Particle> particles;
}
```

We will need more attributes to control this array and its particles:

```java
public class ParticleSystem extends GameObject {

    private List<Behavior<Particle>> pBehaviors;
    private List<Particle> particles;

    private int nbMaxParticle = 20;
    private boolean restart = false;
}
```

The `nbMaxParticle` is the number of particles managed by this `ParticleSystem`, and the `restart` flag will be used to
define if a dead `Particle` (`alive==false`) must be reassigned with new values. This last feature will be very useful
to animate repetitive animation like rain, snow ot fire.

And to initialize this new PArticleSystem object, we will need to explain HOW to create Particle to populate the
particles array. This hard work wil be assigned to the `create()`method:

```java
public class ParticleSystem extends GameObject {
    //...
    public ParticleSystem create(int nb) {
        nbMaxParticle = nb;
        for (int i = 0; i < nbMaxParticle; i++) {
            Particle p = new Particle();
            onCreateParticle(p);
            particles.add(p);
        }
        return this;
    }
    //...
}
```

And the way to initialize a particle is done through the onCreateParticle. The `onCreateParticle` method will verify and
call the corresponding behaviors to initialize the particle attributes.

```java
public class ParticleSystem extends GameObject {
    //...
    private void onCreateParticle(Particle p) {
        if (pBehave.size() > 0) {
            pBehave.forEach(b -> b.onCreate(p));
        }
    }
    //...
}
```

And as soon as the particles are initialized, we need to animate them. We will override the `GameObject#update()` method
tp do this:

```java
public class ParticleSystem extends GameObject {
    //...
    @Override
    public void update(long dt) {
        super.update(dt);
        if (pBehave.size() > 0) {
            for (Particle particle : particles) {
                pBehave.forEach(b -> b.onUpdate(particle, dt));
                if (particle.life <= 0 && getRestart()) {
                    onCreateParticle(particle);
                }
            }
        }
    }
    //...
}
```

So, the create and update particle process are totally delegated to some Behavior. let's see how to create one.

## the Particle Behavior control

Since the ParticleSystem is totally delegating particle creation and animation to a Behavior, let's see how to create
and use one.

```java
public class BasicParticleBehavior implements Behavior<Particle> {

    private ParticleSystem parent;
    private long defaultLifeTime = 1000;
    private Color defaultColor = Color.WHITE;

}
```

The first thing we will need to animate a particle is to know which GameObject is its `parent`. at creation time, we
will also need some basic initial behavior information like the `defaultLifeTime`, the life duration a new particle must
be created with. And for our basic need, we will se a `defaultColor` to display our new particles.

### Create

TODO

### Update

TODO

## Drawing Particle ?

TODO

### Principle

As seen before, the `RenderHelper` is the way we implement our specific rendering process according to the type of
`GameObject` we need to renderer. So to renderer some  `ParticleSystem` we must provide a dedicated implmentation of the
RenderHelper interface to draw the requested particles.

Let's implement a default Particle rendering process.

### ParticleRenderHelper

If we need to draw particles from a ParticleSystem, we must first explain we are going to process `ParticleSystem` :

```java
public class ParticleSystemRenderHelper extends AbstractRenderHelper implements RenderHelper<ParticleSystem> {
    @Override
    public String getType() {
        return ParticleSystem.class.getName();
    }
}
```

And then, we have to parse all the Particle items in the particles array to draw those simple ones. Here we are going to
draw particles as simple colored points.

```java
public class ParticleSystemRenderHelper extends AbstractRenderHelper implements RenderHelper<ParticleSystem> {
  //...
  @Override
  public void draw(Graphics2D g, ParticleSystem go) {
    if (g != null) {
      go.particles.forEach(p -> drawPoint(g, p.position, p.size, p.color));
    }
  }
  //...
}
```

### Using particle ?

TODO

## Special Effects

### Fire

Fire is a very simple animation, just pop particle around a small area and apply a default negative and vertical
acceleration with a small random horizontal deviation and set a short life duration, and repeat ;)

A first basic implementation will set a mono color. but going further, we will apply a color range variation according
to the particle life duration, moving from yellow, to range and then red, simulating the flam and finally gray to figure
out the smoke.

```text
+--------+--------+-------+-------------------+
| yellow | orange |  red  |        gray       |
+--------+--------+-------+-------------------+
Start                 ==>                   End
```

_figure 1 - the color range used to animate flam color._

But we will separate animation and rendering.

Animation wil be delegated to dedicated `Behavior`, and rendering, delegated to special `RenderHelper`.

The Fire SFX[^1]


### Rain

TODO


[^1]: SFX stands for Special eFfect, and the _X_ is just a fancy decoration ;)