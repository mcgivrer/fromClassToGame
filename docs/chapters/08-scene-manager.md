# Scene Manager

Add Scene container to describe a Game state.

A Scene can be a Menu Screen, a Map screen, the Play screen or any specific gameplay a game need to have.

- Add a Scene component
- Add SceneManager to manage switch.

## A Scene

A scene will contain its own `objectList` of `GameObject`, a name, an `update()` method, an `input()` method.

It will have an `initialize()` method called just after instantiation, an a `create()` method called at activation.

A `dispose()` method will be called at end of game juste before closing.

## SceneManager

The `SceneManager` will support all `Scene`, and can switch between each one.

It is initialized with classes names of each Scene, and at activation, will instantiate the corresponding class and store it into a `scenes` buffer, to be able to switch between already existing ones. the `current` scene will be the active one.

The active `Scene` will have an 'active' boolean flag.

An `initialize()` method will be called at `Game` initialization time.

A `dispose()` method will be called at `Game` exit to release all loaded resources from scenes, calling all `Scene.dispose()`.
