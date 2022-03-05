# Adding Lights

Any game must have some rendering artifact to simulate lights. Here we are going to implements some basic lights to
illuminate the game scene.

We won't use 3D effects, but juste some trick to light the scene.

A light can be :

- an ambient one, all the scene is lighted by this one,
- a Spherical one, displaying a simple rounded point of light with a specific size,
- a Conic light like a spot, to renderer fake suspended light, or some light project.

## The LightObject class

So the object class we are going to design will be a enhanced GameObject with

- a light type parameter,
- a light foreground color,
- an intensity parameter,
- and to simulate light glittering, like a flam can glitter in the light wind, a glitterEffect parameter.

```java
public class LightObject extends GameObject {
    public LightType lightType;
    public Color foregroundColor;
    public Double intensity;
    public Double glitterEffect;

    public Color[] colors;
    public float[] dist;
    public RadialGradientPaint rgp;
}
```

## The LightObject rendering

To be able to draw those new object, we need to enhance our `Render` system by adding a new `RenderHelper` dedicated to
the `LightObject`.

We need to implement 3 different rendering process according to the 3 types of light.

- Ambient Light with will be a composite rendering over the camera position with a requested intensity and the requested
  foreground color,
- A spherical light will be rendered with a gradiant sphere with a requested intensity and foreground color
- (TODO) The Conical light or Spot will renderer a gradiant cone with a requested foreground color, intensity and a
  tracking object or a direction.

The `LightObjectRenderHelper.java` file:

```java
public class LightObjectRenderHelper extends AbstractRenderHelper implements RenderHelper<LightObject> {
    public LightObjectRenderHelper(Render r) {
        super(r);
    }

    @Override
    public String getType() {
        return LightObject.class.getName();
    }

    @Override
    public void draw(Graphics2D g, LightObject l) {
        switch (l.lightType) {
            case LIGHT_SPHERE:
                drawSphereLight(g, l);
                break;
            case LIGHT_CONE:
                drawConeLight(g, l);
                break;
            case LIGHT_AMBIANT:
                drawAmbientLight(g, l);
                break;
            default:
                break;
        }
        l.rendered = true;
    }
}
```

TODO : explain the different draw methods.