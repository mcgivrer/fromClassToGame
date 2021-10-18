Feature: PhysicEngine can be configured

  The `PhysicEngine` System's can be configured from `config.properties` file.

  Scenario: PhysicEngine world is default configured
    Given the Configuration is loaded from 'test-pe'
    When the PhysicEngine is created
    Then the PhysicEngine default world width is 320
    And the PhysicEngine default world height is 200
    And the PhysicEngine default world gravity is 0.0,-0.981
    