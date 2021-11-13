Feature: A Scene can have one or more camera
  A Scene support a list of Camera's with an active one.

  Scenario: 01 - I can add Camera's to the Scene
    Given the Game is started with config "camera-1-scene"
    And I can activate the scene "testCamera"
    Then the SceneManager has 1 scene(s)
    And the camera "cam01" is declared.
    And the camera "cam02" is declared.

  Scenario: 02 - The first added camera is the active one
    Given the Game is started with config "camera-1-scene"
    And I can activate the scene "testCamera"
    Then the SceneManager has 1 scene(s)
    And the camera "cam01" is active.

  Scenario: 03 - The camera is centering to its target
    Given the Game is started with config "no-scene"
    And I add a Scene named "followingCamTest";
    And I add a GameObject named "target" at (100,100) sizing (10,10) to Scene "followingCamTest"
    And I add a Camera "cam01" targeting "target" with factor of 0.05 to Scene "followingCamTest"
    And the Camera "cam01" has a viewport of (320,200) from scene "followingCamTest"
    And I can activate the scene "followingCamTest"
    And I update scene "followingCamTest" for 1000 times
    And the Camera "cam01" position is centered on the "target" position.
