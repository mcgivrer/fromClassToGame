Feature: U2300 - A Scene can handle multiple lights

  Scenario: U2301 - Add an ambient light to the scene
    Given the Game is started with config "test-light"
    And I add the LightObjectRenderHelper
    And I activate the scene "testLight"
    And I add a Camera named "cam01" to the current Scene
    And I add an ambient LightObject named "light01"
    And I set the light "light01" intensity to 1.0
    Then the game renders the scene
    And The LightObject "light01" is rendered

  Scenario: U2302 - Add an spherical light to the scene
    Given the Game is started with config "test-light"
    And I add the LightObjectRenderHelper
    And I activate the scene "testLight"
    And I add a Camera named "cam02" to the current Scene
    And I add an spherical LightObject named "light02"
    And I set the light "light02" color to "WHITE"
    And I set the light "light02" intensity to 1.0
    And I set the light "light02" glitterEffect to 1.0
    Then the game renders the scene
    And The LightObject "light02" is rendered
