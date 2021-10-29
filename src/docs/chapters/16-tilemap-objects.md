---
title: From a Class to Game
chapter: 16 - TileMap and its objects
author: Frédéric Delorme
description: |
  Building a platform game means to create and play through |
  levels. Tilemap is a way to define levels and allis objects.
created: 2021-08-01
tags: gamedev, camera, target
---

## TileMap and objects

The Tile map is a map of tiles and objects (sic) !
yes that's true, and I am going to prove it !

When you play to a platform game, you go through multiple play levels, and multiple worlds.
The game is build of many tilemap and objects to display those beautiful levels.

A Tile a basic squared graphical element, and adding more and more of those tiles to a larger window
you would get a full play level. 

Basically,here is the view of the player :

![The player viewport](images/tilemap-diagram-1.jpg "ThePLayer viewport")

And some tiles are added to build the level design:

![Some Tiles in the design](images/tilemap-diagram-2.jpg "Some Tiles in the design")

But looking at details you will distinguish multiple elements on this view port:

![Tile and Objects](images/tilemap-diagram-3.jpg "Tiles and Objects")

1. a simple coin, described by the level tilemap,
2. a chest containing miraculous artifacts, also described at the tilemap level,
3. The PLayer initial position will be set into the tilemap too.
4. but the score and the life number are not set in the tilemap, there are Head Up Display (HUD) details, managed by the game itself.

And finally, if you look carefully to the level design, it is clearly larger than the simple player viewport display:

![Full level design](images/tilemap-diagram-4.jpg "Full level design")

You will notice that the already seen tiles from the previous diagram are marked in red,
and the rest of the full level is not displayed, but clearly described by that tilemap.
