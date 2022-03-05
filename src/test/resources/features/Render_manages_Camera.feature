Feature: The Render system handle camera to render GameObject's.

  The Render system can handle a current camera to render GameObject' list from this point of view

  @Render @System @Camera
  Scenario: The Render has a Camera
    Given the Game is instantiated with config "test-render"
    And the Game is running
    And I activate the scene "testrender"
    And I add a Camera named "cam01" to the current Scene
    Then the Render has its current camera set with "cam01" Camera.