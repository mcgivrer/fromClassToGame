---
title: From a Class to Game chapter: 100 - Architecture author: Frédéric Delorme description: Some details about global
architecture for the Game framework. created: 2021-08-01 tags: gamedev, architecture
---

## Architecture

The game has now an internal architecture we need to draw to well understand the links between components.

Here is the core package internal architecture:

```plantuml
@startuml "Game Internal Architecture"
hide members
hide methods

package "fr.snapgames.fromclasstogame" {
    package "core" <<Frame>>{

        class Game
        note right of Game: This is the main class o fthe framework
        package "config" <<Frame>>{
            class Configuration
        }
        package "io" <<Frame>>{
            class ResourceManager
            class InputHandler
        }
        package "scene" <<Frame>>{
            interface Scene
            class AbstractScene implements Scene
            class SceneManager
        }
        package "gfx" <<Frame>>{
            class Render
            note right of Render : The render class is the graphics drawer
            interface RenderHelper
            class GameObjectRenderHelper implements RenderHelper
            class TextObjectRenderHelper implements RenderHelper
            class Window
        }

        package "entity" <<Frame>>{
            interface Entity
            class GameObject implements Entity
            class TextObject extends GameObject
        }

        Game -- Configuration:config
        Game -- ResourceManager:resMgr
        Game -- SceneManager:sceneMgr
        Game -- Render:render
        Game -- Window:window
        Game -- InputHandler:ih
        SceneManager --> Scene:scenes
        Scene --> GameObject:objects
        Render --> RenderHelper:renderHelpers
        Render --> GameObject:objects
        GameObject -- Color
        GameObject -- BufferedImage
        Render -- Dimension
        Render -- Graphics2D
        ResourceManager -- BufferedImage
        Window -- JFrame
    }
}
@enduml
```

_Core package internal architecture_

### An implementation

The following diagram expose our first Demo implementation.
Not all aspect of our components and systems are exposed but most of these classes usage are described here.

```plantuml
@startuml "Demonstration"
hide members
hide methods
package "fr.snapgames.fromclasstogame" {
    package "core"{
        package "scene"{
            AbstractScene -- GameObject:objects
        }
        package "gfx" {
            interface RenderHelper
            class AbstractRenderHelper
            Render -- RenderHelper:renderHelpers
        }
    } 
    package "demo" {
        package "render"{
            class TextRenderHelper extends AbstractRenderHelper implements RenderHelper
            class LifeRenderHelper extends AbstractRenderHelper implements RenderHelper
            class ScoreRenderHelper extends AbstractRenderHelper implements RenderHelper 
        }
        package "entity" {
            class ScoreObject extends GameObject
            class LifeObject extends GameObject
            class TextObject extends GameObject
        }
        package "scenes"{
            class DemoScene extends AbstractScene
        }
    }
}
@enduml
```

_A demonstration of a standard implementation_
