Feature: Scene Management

    As a game developer, I can manage Scene into a Game


    Scenario: Adding a Scene
        Given the Game is instantiated
        And the Game is running
        And I add a "fr.snapegames.fromclasstogame.test.TestScene" named "test01"
        Then the SceneManager has 1 scene(s)
        And I can activate the scene "test01"

    Scenario: Adding multiple Scene
        Given the Game is instantiated
        And the Game is running
        And I add a "fr.snapegames.fromclasstogame.test.TestScene" named "test01"
        And I add a "fr.snapegames.fromclasstogame.test.TestScene" named "test02"
        And I add a "fr.snapegames.fromclasstogame.test.TestScene" named "test03"
        Then the SceneManager has 3 scene(s)

    Scenario: Switching between multiple Scene
        Given the Game is instantiated
        And the Game is running
        And I add a "fr.snapegames.fromclasstogame.test.TestScene" named "test01"
        And I add a "fr.snapegames.fromclasstogame.test.TestScene" named "test02"
        And I add a "fr.snapegames.fromclasstogame.test.TestScene" named "test03"
        Then the SceneManager has 3 scene(s)
        And I can activate the scene "test01"
        And I wait 2 second(s)
        And I can activate the scene "test02"
        And I wait 2 second(s)
        And I can activate the scene "test03"

    Scenario: Disposing Scenes
        Given the Game is instantiated
        And the Game is running
        And I add a "fr.snapegames.fromclasstogame.test.TestScene" named "test01"
        And I add a "fr.snapegames.fromclasstogame.test.TestScene" named "test02"
        Then the SceneManager has 2 scene(s)
        And I can activate the scene "test01"
        And I wait 2 second(s)
        And I can activate the scene "test02"
        And I wait 2 second(s)
        Then I can dispose all scenes.
