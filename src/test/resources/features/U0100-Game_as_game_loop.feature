Feature: U0100 - The Game has a GameLoop

  All the Game has historical loop inside.

  @GameLoop
  Scenario: U0101 - The Game process input
    Given the Game is instantiated
    Then the Game is running
    And the game can process input

  @GameLoop
  Scenario: U0102 - The Game update its elements
    Given the Game is instantiated
    Then the Game is running
    And I update 2 times the scene
    And the Game time is greater than 0

  @GameLoop
  Scenario: U0103 - The Game render things to screen.
    Given the Game is instantiated with config "test-10-scene.properties"
    Then the Game is running
    And the Game has rendered its 10 graphics elements.
