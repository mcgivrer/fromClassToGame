Feature: A Window can switch from and to Full screen mode

  As a Game is started, its window can be switched between windowed mode and Full screen mode.

  Scenario: A Window is opened in Windowed mode
    Given A Window is created
    Then the window is in Windowed mode

  Scenario: A Window is opened in Windowed mode
    Given A Window is created
    When I switched window to full screen mode
    Then the window is in fullscreen mode

  Scenario: THe window can be closed
    Given A Window is created
    When I request to close the window
    Then the window is closed
