---
title: From a Class to Game 
chapter: 13 - A Basic Physic Engine 
author: Frédéric Delorme 
description: Adding a Camera to the scene will allow you to move in as you want, and follow a target. 
created: 2021-08-01 
tags: gamedev, camera, target
---

## A Basic Physic Engine

The BPE[^1] is very basic one using only position and speed to move object. The mechanic formula we will use in the
computation of object moves will take only in account the velocity and th e current positio nof an oibject to compute
its own next position.

```Math
p_1 = p_0 + v_0 * t
```

where:

- `p1` is the next position of the GameObject,
- `p0` is the current position,
- `v0` i sthe current velocity,
- `t` is the elapsed time since previous computation.

But to be able to correctly simulate the move behavior, we will add 2 new parameters: _friction_ and _bouncyness_.

But those factor will be applied only if there is some collision or point of contact between 2 objects.

```Math
p_1 = p_0 + (v_0 + g) * b * f * t
```

Where:

- `g` is the gravity
- `b` the bouncyness factor, corresponding to the bumping effect
- `f` is the friction, when the object collide something, the velocity is reduced according to this factor.
  [^1]: The **B**asic **P**hysic **E**ngine will be named BPE.

and to get a real break effect on the object according to the friction, we will recompute the velocity with this
attenuation factor:

```Math
v_1 = v_0 * b * f
```

here we see the following variables:

- `v1` the next velocity value,
- `v0` the current GameObject velocity,
- `b`, and `f`, the already existing factors.

And now the next `p1` position will be computed as bellow :

```Math
p_1 = p_0 + (v_1 + g) * t
```

the `g` gravity factor is a force.

In a next chapter we will enhanced this physic and mechnanic computation engine with a new bundle of forces that can be
added at any time to
a [`GameObject`](../../../src/main/java/fr/snapgames/fromclasstogame/core/entity/GameObject.java "go a see code for the GameObject class")
.

### Some C0D3 ?

The implementation of such engine can be dne throught mulitple solution. We will use one that is very simple.

First we are going to define a `World` where our `GameObject` will freely evolve.

And to our existing GameIObject we are going to add the new attributes we discovered through our math formula, but not
directly, we will add a new dimention to our design: a `Material`.

This class will provide all information about physc and mechanic characteristics our engine will need to compute next
position.

first thing first, the `World` object.

### World

the `World` class will provide some basic information about the world our object will moved on: `gravity` is our first
one. Its also define the limit size of the game world with a width and height.

```java
public class World {
    private double gravity;
    private double width;
    private double height;
}
```

### Material

Our `GameoObject`s must contain some physic attributes. And to be able to set the same value on multiple object, we will
introduce a man in the middle: the `Material`. The `Material` class will contain, as seen before, all the attributes
needed by our engine to compute next object position.

```java
public class Material {
    private String name;
  public double bounciness;
  public double friction;
}
```

We need a `name` for this material, and as the formula gives us the characteristics, we need to add `bouncyness`
and `friction` attributes.

Ok, we have our needed info. let's dive into computation:

Our `GameObject`  position and speed will be updated with these new `Material` attribute:

```java
public class GameObject {
  ...
    public double mass = 0.0;
    public double contact = 0.0;
    public Material material;
  ...
}
```

## Computation into the Engine

The `PhysicEngine` class will compute the `GameObject`'s position according to those parameters:

```java
public class PhysicEngine {
  public Game game;
  public World world;
  public List<GameObject> objects = new ArrayList<>();
  
  public PhysicEngine(Game g) {
    this.game = g;
  }
  
  public void update(GameObject go, double dt) {
    go.dx = go.dx * (go.contact * go.material.
            friction * go.material.bouncyness);
    go.x += go.x * (go.dx + this.gravity) * dt;
  }
  
  public void add(GameObject go) {
      this.objects.add(go);
  }
}
```

### Enhancing the simulation

To get a better simulation, we will need to distinguish 2 kind of friction:

- the static one, happening on the floor,
- the dynamic one for objects collision.

#### Upgrading Material

So our Material will provide such new attributes in place of the old friction :


- `bouncyness` is the "spring" factor of our object, it's its capability to throw energy reacting a to hit with another
  object,
- `dynFriction` is the ratio of energy lost while the object bump into another,
- `staticFriction` is a friction ratio when object bump into static object.
- `density` is the physic material density.

```java
public class Material {
    private String name;
    private double bouncyness;
    private double staticFriction;
    private double dynFriction;
}
```

and the World update method formula will be updated with that:

#### PhysicEngine update

So now, the `PhysicEngine#update(double)` must be updated was following:

```java
public class PhysicEngine {
  ...
  public void update(double dt) {
    if (!go.relativeToCamera) {
      go.dx = go.dx * go.material.staticFriction;
      go.dy = (go.dy + go.gravity + (world.gravity * 0.11)) * go.material.staticFriction * 1 / go.mass;

      go.x += go.dx * dt;
      go.y += go.dy * dt;
    }
  }
  ...
}
```

#### The Default materials

TODO create the Default Material builder enum to manager default values for standard materials like wood, rock, rubber,
water, air.
