Feature: U0350 - A Window can switch from and to Full screen mode

  As a Game is started, its window can be switched between windowed mode and Full screen mode.

  @windowed
  Scenario: U0351 - A Window is opened in Windowed mode
    Given A Window is created
    Then the window is in Windowed mode

  @fullscreen @windowed
  Scenario: U0352 - A Window is opened in Windowed mode
    Given A Window is created
    When I switched window to full screen mode
    Then the window is in fullscreen mode

  @ignore @multiscreen
  Scenario: U0353 - A Window can be switched between screens
    Given A fullscreen Window is created
    When multiple screens are available
    And I switch between screens
    Then Window is in fullscreen on the next screen.
