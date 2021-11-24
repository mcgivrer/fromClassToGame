Feature: A Scene can have one or more GameObject

  Scenario: 01 - I want to add a GameObject to a scene
    Given the Game is instantiated with config "no-scene"
    And the Game is running
    And I add a "TestScene01:fr.snapgames.fromclasstogame.test.scenes.TestScene" as Scene
    And I activate the scene "TestScene01"
    And I add a GameObject named "obj1" at (169,100) sizing (16,16) to Scene "TestScene01"
    And the current scene is "TestScene01"
    And the Scene "TestScene01" has 1 GameObject.

