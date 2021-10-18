Feature: The game is running with arguments

  The Game class is started normally by initializing with arguments or default values.

  Scenario: The game is started with default values
    Given the Game is instantiated
    Then the Game is running
    And a window of 320 x 200 is created
    And the window title is "test"

  Scenario Outline: The game is started without arguments but no scale
    Given the Game is instantiated with config "no-scene"
    When I prepare the arguments
    And I add argument "width=<width>"
    And I add argument "height=<height>"
    And I add argument "title=<title>"
    And I add argument "scale=<scale>"
    Then the Game is running
    And a window of <win_width> x <win_height> is created
    And the window title is "<title>"

    Examples:
      | title  | scale | width | height | win_width | win_height |
      | first  | 1.0   | 320   | 200    | 320       | 200        |
      | second | 1.0   | 640   | 400    | 640       | 400        |
      | third  | 2.0   | 320   | 200    | 640       | 400        |


  Scenario: The game is started with a wrong argument
    Given the Game is instantiated
    When I prepare the arguments
    And I add argument "arg=unknown"
    Then the Game raises exception

