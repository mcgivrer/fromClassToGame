Feature: The game has scene with objects

  The Game class is started normaly by initializing with arguments or default values.
  
  Scenario: The Game has one GameObject
    Given the Game is instantiated
    And the Game is running
    And I add a GameObject named "player" at 160.0,100.0
    And the "player" size is 16 x 16
    Then the Game has 1 GameObject at window center.