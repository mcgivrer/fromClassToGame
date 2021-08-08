---
title: From a Class to Game
chapter: 07 - Resource Manager
author: Frédéric Delorme
description: A dedicated service to resource management.
created: 2021-08-01
tags: gamedev, resources, image, font
---

## Resource Manager

As we now need more that just images to be displayed in our game, we need some other kind of resources like font, sound and so on, we need to add a ResourceManager which work consist in managing all this kind of resources.

### Main structure

First we are going to keep in memory all the resources, to retrieve them faster. So let's set a Map to store those `resources`.
For convenient usage, we will also keep a rootPath variable pointing to the `rootPath` of execution class's path.

We will use also a simplified version of the singleton pattern (only ONE ResourceManager to be instantiated at a time). The `instance` attribute will keep this singleton on.

As this service will be singleton, and we won't need an instantiation, we will use only static attributes and methods.

```java
public class ResourceManager {

    private static final Logger logger = LoggerFactory.getLogger(ResourceManager.class);

    private static Map<String, Object> resources = new HashMap<>();
    private static String rootPath;

    private ResourceManager() {
        rootPath = ResourceManager.class.getClassLoader().getResource("/").toString();
    }
}
```

### Reading image

```java
public class ResourceManager {

    ...
    private static BufferedImage readImage(String path) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream(path));
        } catch (IOException e) {
            logger.error("Unable to read image", e);
        }
        return image;
    }


    public static BufferedImage getImage(String imagePath) {
        if (resources.containsKey(imagePath)) {
            return (BufferedImage) resources.get(imagePath);
        } else {
            BufferedImage i = readImage(imagePath);
            if (i != null) {
                resources.put(imagePath, i);
            }
            return (BufferedImage) resources.get(imagePath);
        }
    }

}
```

Adding a _5 stars_ service that consists in slicing the image at getting time is added, and then store it in the cache:

First try to read image in the cache from the already existing `readImage(String)` method. and then Slice the image with the described rectangle with `(x,y,w,h)` where:

- `x` is the horizontal position in the read image,
- `y` the vertical position in the read image,
- `w` the width of the image to be sliced,
- `h` the height of the image to be sliced.

```java
    private static BufferedImage readImage(String imagePath, int x, int y, int w, int h) {
        BufferedImage image = null;
        image = readImage(imagePath).getSubimage(x, y, w, h);
        return image;
    }

```

And a trick to keep the image in cache memory, using an `internalName` added to the `imagePath` as key of this object in the cache.

```java
    public static BufferedImage getSlicedImage(String imagePath, String internalName, int x, int y, int w, int h) {
        if (resources.containsKey(imagePath + ":" + internalName)) {
            return (BufferedImage) resources.get(imagePath);
        } else {
            BufferedImage i = readImage(imagePath, x, y, w, h);
            if (i != null) {
                resources.put(imagePath + ":" + internalName, i);
            }
            return (BufferedImage) resources.get(imagePath + ":" + internalName);
        }
    }
```

### Read a Font

```java
public class ResourceManager {

    private static final Logger logger = LoggerFactory.getLogger(ResourceManager.class);

    private static Map<String, Object> resources = new HashMap<>();
    private static String rootPath;

    private ResourceManager() {
        rootPath = ResourceManager.class.getClassLoader().getResource("/").toString();
    }

    private static Font readFont(String path) {
        Font font = null;
        try {
            InputStream is = ResourceManager.class.getClassLoader().getResourceAsStream(path);
            font = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            logger.error("Unable to read font", e);
            e.printStackTrace();
        }
        return font;
    }

    private static BufferedImage readImage(String path) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream(path));
        } catch (IOException e) {
            logger.error("Unable to read image", e);
        }
        return image;
    }

    private static BufferedImage readImage(String path, int x, int y, int w, int h) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream(path)).getSubimage(x, y, w, h);
        } catch (IOException e) {
            logger.error("Unable to read image", e);
        }
        return image;
    }

    public static Font getFont(String fontPath) {
        Font f = null;
        if (resources.containsKey(fontPath)) {
            f = (Font) resources.get(fontPath);
        } else {
            f = readFont(fontPath);
            if (f != null) {
                resources.put(fontPath, f);
            }
        }
        return f;
    }

    public static BufferedImage getImage(String imagePath) {
        if (resources.containsKey(imagePath)) {
            return (BufferedImage) resources.get(imagePath);
        } else {
            BufferedImage i = readImage(imagePath);
            if (i != null) {
                resources.put(imagePath, i);
            }
            return (BufferedImage) resources.get(imagePath);
        }
    }

    public static BufferedImage getSlicedImage(String imagePath, String internalName, int x, int y, int w, int h) {
        if (resources.containsKey(imagePath + ":" + internalName)) {
            return (BufferedImage) resources.get(imagePath);
        } else {
            BufferedImage i = readImage(imagePath, x, y, w, h);
            if (i != null) {
                resources.put(imagePath + ":" + internalName, i);
            }
            return (BufferedImage) resources.get(imagePath + ":" + internalName);
        }
    }
}
```

### Usage

Very simple to use it:

* To load an image :

```java
BufferedImage img = ResourceImage.getImage("images/my_image.png");
```
You can also slice an image at reading. The image will be store in the sliced version.

```java
BufferedImage img = ResourceImage.getSlicedImage("images/my_image.png","myImage",0,0,16,16);
```

The _my_image.png_ will be sliced at position `0,0` with a size of `16x16`, and will be stored in resource cache as _myImage_.

To retrieve this image later, you will just have to get _images/my_image.png:myImage_.

* To load a font :

```java
Font myFont = ResourceImage.getFont("fonts/a_font.ttf");
```

You can directly modify the get font :

```java
Font myFont = ResourceImage.getFont("fonts/a_font.ttf").defivedFont(14.0);
```

And that's It !
