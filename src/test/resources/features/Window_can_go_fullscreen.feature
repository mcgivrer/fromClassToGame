Feature: A Window can switch from and to Full screen mode

  As a Game is started, its window can be switched between windowed mode and Full screen mode.

  Scenario: A Window is opened in Windowed mode
    Given A Window is created
    Then the window is in Windowed mode

  Scenario: A Window is opened in Windowed mode
    Given A Window is created
    When I switched window to full screen mode
    Then the window is in fullscreen mode

  Scenario: A Window can be switched between screens
    Given A fullscreen Window
    When multiple screens are available
    And I switch between screens
    Then Window is in fullscreen on the next screen.