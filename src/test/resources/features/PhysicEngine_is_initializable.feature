Feature: PhysicEngine can be configured

  The `PhysicEngine` System's can be configured from `config.properties` file.

  @System @PhysicEngine
  Scenario: 01 - PhysicEngine world is default configured
    Given the Configuration is loaded from 'test-pe'
    When the PhysicEngine is created
    Then the PhysicEngine default world width is 320
    And the PhysicEngine default world height is 200
    And the PhysicEngine default world gravity is 0.0,-0.981

  @System @PhysicEngine
  Scenario: 02 - PhysicEngine has no objects by default
    Given the Configuration is loaded from 'test-pe'
    And the PhysicEngine is created
    Then the PhysicEngine has no object


  @System @PhysicEngine @GameObject
  Scenario: 03 - PhysicEngine can support multiple GameObject's
    Given the Game is instantiated with config "test-pe"
    And the Game is running
    And I activate the scene "test"
    And I add a GameObject named "testPEGo1" at (100.0,100.0)
    And I add a GameObject named "testPEGo2" at (120.0,100.0)
    And I add a GameObject named "testPEGo3" at (140.0,100.0)
    Then the PhysicEngine has 3 objects

