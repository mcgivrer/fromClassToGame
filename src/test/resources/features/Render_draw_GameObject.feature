Feature: The Render system is dedicated to the GameObject rendering.

  All the GameObject managed by the Game are delegated for rendering to the Render System

  @Render @System
  Scenario: The Render class is a System
    Given the Game is instantiated with config "test-render"
    And the Game is running
    And I activate the scene "testrender"
    Then the Render is ready
    And the Render is a System.

  @Render @System
  Scenario: the Render draw GameObject
    Given the Game is instantiated with config "test-render"
    And the Game is running
    And I activate the scene "testrender"
    And I add a GameObject named "testRenderedGO" at (100.0,100.0)
    And I update 1 times the scene
    Then the GameObject named "testRenderedGO" is rendered.

