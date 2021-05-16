Feature: The game is operating normaly by initializing, then looping unti exit is requeted.

  Scenario: The game is initialized
    Given the Game is instanciated
    When I prepare the arguments
    And I add arg "width=320"
    And I add arg "height=200"
    And I add arg "title=MyGame"
    Then the Game is running 
    And a window of 320 x 200 is created
    And the title is "MyGame"
    
