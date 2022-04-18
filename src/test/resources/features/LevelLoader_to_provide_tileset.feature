Feature: Loading a Level from its *.level file

  The `LevelLoader` service is dedicated to load objects from a level file and initialize all corresponding entities.

  Scenario: 01- loading a TileSet
    Given the LevelLoader is instantiated
    And the level file "/levels/test-level.properties" is loaded
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
