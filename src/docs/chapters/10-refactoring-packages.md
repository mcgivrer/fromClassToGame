# Refactoring packages

As far as we go, the code start having multiple classes with dedicated roles.
It's now time to organize our work through java package to isolate and document through some
domains our code.

```
fr.snapgames.fromcalsstogame
    .core
       .config
       .entity
       .exceptions
       .gfx
       .io
       .scenes
       Game
    .demo
       .scenes
          DemoScene 
```

Two main packages are now splitting our classes: 

- The `core` package will contain all classes dedicated to the Game framework,
- The `demo` package will host all sample classes demoing the framework ones.  

