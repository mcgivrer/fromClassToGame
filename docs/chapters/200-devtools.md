# DevTools

To be able to help the designer and the graphiste guy, we sometimes need some tooling.

A TileGenerator has been created to generate a template for tilemap design.

just compile the project with a `mvn clean install` and then :

```shell
$ tilgen.sh tileW=16 tileH=16 \
  cols=20 rows=20 \
  backColor=#000000
```

The options are:

| argument  | description                               | default                  |
|:---------:|:------------------------------------------|:-------------------------|
|   tileW   | width of a tile                           | 16                       |
|   tileH   | height of a tile                          | 16                       |
|   cols    | number of tile in a row                   | 20                       |
|   rows    | number of tile in a col                   | 20                       |
| filename  | a default configuration file (properties) | tilegenerator.properties |
| backcolor | background color for the generated image  | #000000                  |
| textcolor | text color for the generated image        | #888888                  |
| linecolor | line color for the generated image        | #444444                  |

