# HOW TO

This small file intends to answers some of the basic question about this program.

# run the demo

Execute the following command in any terminal:

```bash
java -jar fromClassToGame-[X.Y.Z{-fix}{-SNAPSHOT}]-shaded.jar
```

where :
- `X.Y.Z` is the mandatory current release,
- `{-fix}` is the optional fix number,
- `{-SNAPSHOT}` for optional current Work in progress release.

## Keyboards

Available actions:

Main keys (in *AbstractScene*) :
- `F3` Save screenshot to jar path `./screenshots`
- `F11` Switch between Windowed / Fullscreen
- `Ctrl`+ `F11` switch gale between multiple screens (when multiscreen available)
- `P` or `PAUSE` switch game to pause mode

Player keys (in *DemoScene*):
- `UP`,`DOWN`,`LEFT`,`SPACE`, move left, right and jump player, stop player.
- `Z` reset scene
- 

Debug functions keys (in *AbstractScene* and some Debug *behaviors*)
- `D` Activate and rotate global debug level for visual debug display information,
- `F` Stop/Start `ParticleSystem` animation,
- `TAB` switch debug focus on next current active scene's `GameObject`
- `BACKSPACE` switch-back debug focus on previous current active scene's `GameObject`
- `N` up debug level on current `GameObject`
- `B` down debug level on current `GameObject`

