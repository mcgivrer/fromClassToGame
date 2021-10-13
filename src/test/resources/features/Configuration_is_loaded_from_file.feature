Feature: The configuration is loaded from a properties file

  All the default configuration values are loaded from a java properties file

  Scenario: Load a set of default values
    Given the Configuration object is initialized with "config-test"
    Then the properties are loaded
    And the default title is "config-test"
    And the default game width is 320
    And the default game height is 200
    And the default game scale is 1
    And the default screen is -1
    And the default world gravity is 0.0,-0.981
    And the scene "test1" is "fr.snapgames.fromclasstogame.test.scenes.TestScene"
    And the scene "test2" is "fr.snapgames.fromclasstogame.test.scenes.TestScene"
    And the scene "test3" is "fr.snapgames.fromclasstogame.test.scenes.TestScene"
    And the default scene is "test1"