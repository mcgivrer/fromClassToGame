Feature: Any GamesObject can have some material assigned

  In the Game process to animate GameObject, PhysicEngine needs physic data. Those data are coming from the
  Material Object. Any GameObject can have an assigned Material.

  Scenario: 01 - The Entity has material
    Given the Game is instantiated
    And the Game is running
    And I activate the scene "test1"
    And I add a GameObject named "player" at (160.0,100.0)
    And I set Material "WOOD" to the GameObject "player"
    Then the GameObject "player" has Material "wood"

  Scenario: 02 - ROCK Material has some specific Physic values
    Given A Material of type "ROCK"
    Then  the Material has a bounciness of 0.6
    And the Material has a dynamic friction of 1
    And the Material has a static friction of 1
    And the Material has a density of 1

  Scenario: 03 - WOOD Material has some specific Physic values
    Given A Material of type "WOOD"
    Then  the Material has a bounciness of 0.1
    And the Material has a dynamic friction of 0.69
    And the Material has a static friction of 0.69
    And the Material has a density of 0.3

  Scenario: 04 - METAL Material has some specific Physic values
    Given A Material of type "METAL"
    Then  the Material has a bounciness of 0.05
    And the Material has a dynamic friction of 1
    And the Material has a static friction of 1
    And the Material has a density of 1.2

  Scenario: 05 - RUBBER Material has some specific Physic values
    Given A Material of type "RUBBER"
    Then  the Material has a bounciness of 0.8
    And the Material has a dynamic friction of 0.88
    And the Material has a static friction of 0.98
    And the Material has a density of 0.3

  Scenario: 06 - GLASS Material has some specific Physic values
    Given A Material of type "GLASS"
    Then  the Material has a bounciness of 0.4
    And the Material has a dynamic friction of 1
    And the Material has a static friction of 1
    And the Material has a density of 1

  Scenario: 07 - ICE Material has some specific Physic values
    Given A Material of type "ICE"
    Then  the Material has a bounciness of 0.1
    And the Material has a dynamic friction of 0.1
    And the Material has a static friction of 1
    And the Material has a density of 1

  Scenario: 08 - AIR Material has some specific Physic values
    Given A Material of type "AIR"
    Then  the Material has a bounciness of 1
    And the Material has a dynamic friction of 1
    And the Material has a static friction of 1
    And the Material has a density of 0.01

  Scenario: 09 - STATIC Material has some specific Physic values
    Given A Material of type "STATIC"
    Then  the Material has a bounciness of 0
    And the Material has a dynamic friction of 0
    And the Material has a static friction of 0
    And the Material has a density of 0

  Scenario: 10 - NEUTRAL Material has some specific Physic values
    Given A Material of type "NEUTRAL"
    Then  the Material has a bounciness of 1
    And the Material has a dynamic friction of 1
    And the Material has a static friction of 1
    And the Material has a density of 1
