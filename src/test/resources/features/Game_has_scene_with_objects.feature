Feature: The game has scene with different types of GameObject.

  The Game class is started normally by initializing with arguments or default values.

  @SceneManager @Scene
  Scenario Outline: 01 - The Game has one GameObject
    Given the Game is instantiated
    And the Game is running
    And I activate the scene "test1"
    And I add a GameObject named <name> at (<x>,<y>)
    And the <name> size is <w> x <h>
    Then the Game has <i> GameObject at window center.
    Examples:
      | name     | x     | y     | w  | h  | i |
      | "player" | 160.0 | 100.0 | 16 | 16 | 1 |

  @SceneManager @Scene
  Scenario: 02 - The Game has multiple GameObjects
    Given the Game is instantiated
    And the Game is running
    And I activate the scene "test1"
    And I add a GameObject named "player" at (160.0,100.0)
    And I add a GameObject named "enemy_1" at (10.0,30.0)
    And I add a GameObject named "enemy_2" at (10.0,10.0)
    Then the Game has 3 GameObject(s).

  @SceneManager @Scene
  Scenario: 03 - The Game can move GameObject with default gravity by changing velocity
    Given the Game is instantiated
    When I prepare the arguments
    And I add argument "fps=60"
    And the Game is running
    And I activate the scene "test1"
    And I add a GameObject named "player" at (160.0,100.0)
    And the "player" GameObject velocity is set to (5,5)
    Then I update 2 times the scene
    And the "player" GameObject is now at (162.0,102.0)
    Then I update 2 times the scene
    And the "player" GameObject is now at (164.0,104.0)

  @TextObject
  Scenario: 04 - The Game can have TextObject
    Given the Game is instantiated
    And the Game is running
    And I activate the scene "test1"
    And I add a TextObject named "score" at (10.0,10.0)
    And the text for "score" is "0000"
    Then the Game has 1 GameObject(s).

  @TextObject
  Scenario: 05 - The default TextObject text color is white
    Given the Game is instantiated
    And the Game is running
    And I activate the scene "test1"
    And I add a TextObject named "score" at (10.0,10.0)
    Then the TextObject default color for "score" is White

  @TextObject
  Scenario: 06 - there is no default font on TextObject
    Given the Game is instantiated
    And the Game is running
    And I activate the scene "test1"
    And I add a TextObject named "score" at (10.0,10.0)
    Then the TextObject default font for "score" is null
