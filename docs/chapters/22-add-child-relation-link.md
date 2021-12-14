# Adding a child relationship

As for now we process all object as unit. But with the `ParticleSystem`, we already see that `Particle` is a child of
a `ParticleSystem`. And we designed this relation by adding a list of particles to the `ParticleSystem`.

We can manage in a better way this relation by moving the handle to the `GameObject` itself. We will be able to take
advantage of this by managing child objects in a hierarchy.

So first, let's implement some child to the GameObject, and then adapt the ParticleSystem to match those changes.

## GameObject and child

So let's move this strange Particle list at GameObject level:

```java
public class GameObject implements Entity {
    //...
    /**
     * Child objects.
     */
    protected List<GameObject> child = new ArrayList<>();
    //...
}
```

ok, so now to be able to update this child, and render them, `Render` system must render the child element, and
`PhysicEngine` system must update them.

### Render system

The `Render#drawObjectList()` method must be updated to process child element:

```java
public class Render implements System {
    //...
    private void drawObjectList(Graphics2D g, List<GameObject> objects) {
        objects.stream().filter(go -> go.actve)
                .collect(Collectors::toList)
                .forEach(go -> {
                    draw(g, go);
                    // process child
                    go.getChild().stream()
                            .filter(c -> c.active)
                            .collect(Collectors::toList)
                            .forEach(co -> draw(g, co));
                });
    }
    //...    
}

```

### PhysicEngine system

As we render child, we must before update them, let modify the `PhysicEngine#update()` method to process also child
objects.

```java
public class PhysicEngine implements System {
    //...
    public void update(long dt) {
        try {
            if (!game.isPause()) {
                getObjects().stream().forEach(go -> {
                    update(go, dt);
                    go.getChild().forEach(co -> {
                        update(co, dt);
                    });
                });
            }
        } catch (ConcurrentModificationException e) {
            logger.error("Unable to update the GameObjects");
        }
    }
    //...
}
```

## ParticleSystem

Ok, now we are ready to adapt the `ParticleSystem` to take benefit from the new `GameObject` child relationship.

```java
public class ParticleSystem extends GameObject {
    //...
    private void createParticles(int nb) {
        for (int i = 0; i < nb; i++) {
            Particle p = new Particle();
            onCreateParticle(p);
            child.add(p);
        }
    }
    //...
}
```

Now the Particle are created as child of the Particle System, and Behavior<Particle> are applied to all the child.

> **TIPS**<br/>In a next chapter we will let evolve the Behavior design to apply them to child directly from its parent.

