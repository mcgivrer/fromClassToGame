Feature: Scene Management

  As a game developer, I can manage Scene into a Game

  Scenario: 01 - Adding one Scene
    Given the Game is started with config "no-scene"
    And I add a "fr.snapgames.fromclasstogame.test.scenes.TestScene" named "test11"
    Then the SceneManager has 1 scene(s)
    And I activate the scene "test11"

  Scenario: 02 - Adding multiple Scene
    Given the Game is started with config "no-scene"
    And I add a "fr.snapgames.fromclasstogame.test.scenes.TestScene" named "test21"
    And I add a "fr.snapgames.fromclasstogame.test.scenes.TestScene" named "test22"
    And I add a "fr.snapgames.fromclasstogame.test.scenes.TestScene" named "test23"
    Then the SceneManager has 3 scene(s)

  Scenario: 03 - Switching between multiple Scene
    Given the Game is started with config "no-scene"
    And I add a "fr.snapgames.fromclasstogame.test.scenes.TestScene" named "test31"
    And I add a "fr.snapgames.fromclasstogame.test.scenes.TestScene" named "test32"
    Then the SceneManager has 2 scene(s)
    And I activate the scene "test31"
    And I activate the scene "test32"

  Scenario: 04 - Scenes are loaded from a file
    Given the Game is started with config "test-scene"
    Then the SceneManager has 3 scene(s)

  Scenario: 05 - Disposing Scenes
    Given the Game is started with config "test-scene"
    Then the SceneManager has 3 scene(s)
    And I activate the scene "test01"
    And I activate the scene "test02"
    And I activate the scene "test03"
    Then I can dispose all scenes.

