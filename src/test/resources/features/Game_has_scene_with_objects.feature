Feature: The game has scene with different types of GameObject.

  The Game class is started normally by initializing with arguments or default values.

  Scenario: The Game has one GameObject
    Given the Game is instantiated with "default-scene"
    When I prepare the arguments
    And the Game is running
    And I add a GameObject named "player" at (160.0,100.0)
    And the "player" size is 16 x 16
    Then the Game has 1 GameObject at window center.

  Scenario: The Game has multiple GameObjects
    Given the Game is instantiated with "default-scene"
    And the Game is running
    And I add a GameObject named "player" at (160.0,100.0)
    And I add a GameObject named "enemy_1" at (10.0,30.0)
    And I add a GameObject named "enemy_2" at (10.0,10.0)
    Then the Game has 3 GameObject(s).

  Scenario: The Game can move GameObject
    Given the Game is instantiated with "default-scene"
    When I prepare the arguments
    And I add argument "fps=60"
    And the Game is running
    And I set a World of (800.0,600.0) units with gravity of 0.981
    And I add a GameObject named "player" at (160.0,100.0)
    And the "player" GameObject speed is set to (5,5)
    Then I update 2 times the scene
    And the "player" GameObject is now at (320.0,260.0)
    Then I update 2 times the scene
    And the "player" GameObject is now at (480.0,437.0)

  Scenario: The Game can have TextObject
    Given the Game is instantiated with "default-scene"
    And the Game is running
    And I add a TextObject named "score" at (10.0,10.0)
    And the text for "score" is "0000"
    Then the Game has 1 GameObject(s).

  Scenario: The default TextObject text color is white
    Given the Game is instantiated with "default-scene"
    And the Game is running
    And I add a TextObject named "score" at (10.0,10.0)
    Then the TextObject default color for "score" is White

  Scenario: there is no default font on TextObject
    Given the Game is instantiated with "default-scene"
    And the Game is running
    And I add a TextObject named "score" at (10.0,10.0)
    Then the TextObject default font for "score" is null

