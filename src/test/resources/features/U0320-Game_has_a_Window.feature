Feature: U0320 - The Game has a Window

  Any Game object has a window attribute to display thing.

  @Window @System
  Scenario: U0321 - A Game has a Window
    Given the Game is instantiated
    And the Game is running
    Then the Game has a Window.

  @Window @System
  Scenario: U0322 - The Window gets its presets from config file
    Given the Game is instantiated with config "test-window"
    And the Game is running
    Then the Window is get from the Game
    And the window title is "This is a Window title"
    And the Window width is set to 640
    And the Window height is set to 400

  @Window @System
  Scenario: U0323 - The Window can draw a content from Render.
    Given the Game is instantiated with config "test-window"
    And the Game is running
    And the Window is get from the Game
    Then the Window draw the Render content
