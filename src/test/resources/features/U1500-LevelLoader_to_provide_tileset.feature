Feature: U1500 - Loading a Level from its *.level file

  The `LevelLoader` service is dedicated to load objects from a level file and initialize all corresponding entities.

  Scenario: U1501 - loading a Tilemap header
    Given the LevelLoader is instantiated
    And the level file "/levels/test-level-header.properties" is loaded
    Then a level named "test-level-header" is defined
    And the TileMap has an id set to "101"
    And the TileMap has an world set to 1
    And the TileMap has an level set to 1
    And the TileMap has a name set to "test-level-header"
    And the TileMap has a title set to "Tilemap header"
    And the TileMap has a description set to "A test for a Tilemap Header"


  Scenario: U1502 - loading a Tileset
    Given the LevelLoader is instantiated
    And the level file "/levels/test-level-tileset.properties" is loaded
    Then a level named "test-level-tileset" is defined
    And a TileSet with 8 Tiles is created
    And the Tile with code "b" has a "block" attribute
    And the Tile with code "B" has a "block" attribute
    And the Tile with code "h" has a "hike" attribute
    And the Tile with code "O" has a "collectible" attribute
    And the Tile with code "O" has a "type" attribute equals to "coins"
    And the Tile with code "O" has a "value" attribute equals to 5
    And the Tile with code "c" has a "collectible" attribute
    And the Tile with code "c" has a "type" attribute equals to "chest"
    And the Tile with code "c" has a "value" attribute equals to 100

  Scenario: U1503 - loading some GameObject
    Given the LevelLoader is instantiated
    And the level file "/levels/test-level-gameobjects.properties" is loaded
    Then a level named "test-level-gameobject" is defined
    And a GameObject "player" has been created