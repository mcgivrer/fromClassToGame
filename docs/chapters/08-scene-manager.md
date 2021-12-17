# Scene Manager

Add Scene container to describe a Game state.

A Scene can be a Menu Screen, a Map screen, the Play screen or any specific gameplay a game need to have.

- Add a Scene component
- Add SceneManager to manage switch.

## A Scene

A scene will contain its own `objectList` of `GameObject`, a name, an `update()` method, an `input()` method.

It will have an `initialize()` method called just after instantiation, an a `create()` method called at activation.

A `dispose()` method will be called at end of game juste before closing.

> **INFO**<br/>See [Scene_can_have_GameObject.feature](../../src/test/resources/features/Scene_can_have_GameObject.feature) 
> for corresponding implementation test.

## SceneManager

The `SceneManager` will support all `Scene`, and can switch between each one.

It is initialized with classes names of each Scene, and at activation, will instantiate the corresponding class and store it into a `scenes` buffer, to be able to switch between already existing ones. the `current` scene will be the active one.

The active `Scene` will have an 'active' boolean flag.

An `initialize()` method will be called at `Game` initialization time.

A `dispose()` method will be called at `Game` exit to release all loaded resources from scenes, calling all `Scene.dispose()`.

An `activate` method is activating a current scene on its name.

## Loading scene from configuration file

The `SceneManager` contains also a useful utility to load the scene classes list from the configuration file, ans create instances at activation.

The `Scene`'s to be listed in the game engine are loaded from some configuration file attributes and the classes are loaded from classpath while the `intiialize()` method is called from `Game`.

At activation time, the required 'named' scene class is instantiated from already class listed ones and store in the scenes instances list.

Then it is declare `current` and game focus is set on this scene.

> **INFO**<br/>See the - [Game_has_scene_with_objects.feature](../../src/test/resources/features/Game_has_scene_with_objects.feature) test scenarii for functional verification.