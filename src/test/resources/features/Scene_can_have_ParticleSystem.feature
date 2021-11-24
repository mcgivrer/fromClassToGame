Feature: A Scene can have some animated ParticleSystem as GameObject to create special effects

  Scenario: 01 - I can create a ParticleSystem
    Given the Game is instantiated
    And the Game is running
    And I add a "testScenePS:fr.snapgames.fromclasstogame.test.scenes.TestScene" as Scene
    And I activate the scene "testScenePS"
    And I add ParticleSystem object named "testps" to scene "testScenePS"
    And the current scene is "testScenePS"
    Then the Scene "testScenePS" has 1 GameObject.
