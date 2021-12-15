# Enhancing the rendering pipeline

THe fact that we need more and more control on rendering in the helpers, demonstrates how we must update the `Render`
class and all the drawing tools.

Our Helpers will inherit from the `AstractRenderHelper`, and all common render processing will be defined in the
abstract class.

We will specifically design the debug info display in this component to let all inheriting components take benefit of
such common implementation.

## Display Debug Info

In all the `GameObject` and its inherent, a `getDebugInfo()` method returning a list of String must be provided to
prepare debug data to be displayed visually on the `GameObject` side.

parsing all important attributes, the keys/values are added to the list according to their own humanly readable format.

A default one in the `GameObject` its self is implemented.

```java
public class gameObject {
    //...
    public List<String> getDebugInfo() {
        this.debugOffsetX = -40;
        this.debugOffsetY = 10;
        List<String> debugInfo = new ArrayList<>();
        if (debug > 0) {
            debugInfo.add("n:" + name);
            debugInfo.add("dbgLvl:" + debug);
            if (debug > 2) {
                debugInfo.add("pos:" + position.toString());
                debugInfo.add("vel:" + velocity.toString());
                debugInfo.add("acc:" + acceleration.toString());
                if (debug > 3) {
                    debugInfo.add("mass:" + mass);
                    if (material != null) {
                        debugInfo.add("mat:" + material.name);
                        debugInfo.add("frict:" + material.dynFriction);
                    }
                    debugInfo.add("contact:" + getAttribute("touching", false));
                    debugInfo.add("jumping:" + getAttribute("jumping", false));
                }
            }
            debugInfo.add("active:" + (active ? "on" : "off"));
        }
        return debugInfo;
    }
    //...
}
```

It can be overloaded in the extending object; in the Inventory Object we add the inventory size.

```java
public class InventoryObject extends GameObject {
    //...
    @Override
    public List<String> getDebugInfo() {
        List<String> dbgInfo = super.getDebugInfo();
        dbgInfo.add(String.format("size: %d", this.items.size()));
        return dbgInfo;
    }
    //...
}
```

## Rendering debug information

The rendering implementation in the `AbstractRenderHelper` will serve all `GameObject` and its child.

```java
public class AbstractRenderHelper {
    //...
    public void drawDebugInfo(Graphics2D g, GameObject go) {
        // parse the GameObject#getDebugInfo() String List and proceed to the line rendering;
    }
    //...
}
```

And many utilities are implemented and delegated to this class:

- `setFont()` to define current rendering font,
- `setFontSize()` to fine size for the current active font,
- `setColor()` to define front rendering color,
- `drawText()` to draw some text,
- `drawrTextBorder()` to draw a colored border to a specific text,
- `fillRect()` to fill a rectangle,
- `drawRect()` to draw a simple rectangle,
- `drawImage()` to draw an image,
- `drawPoint()` to draw a single point of a defined size,
- `drawGauge()` to draw a bar graph single information.

> **[!] WARN**<br/>These rendering utilities will be moved to the Render class itself in a
> next release (e.g. the 1.0.3 release)

> **[?] INFO**<br/>`2021-DEC-15` The `Render` class has been simplified with having in mind the main goal
> to remove any `Graphics2D` from method signature. 