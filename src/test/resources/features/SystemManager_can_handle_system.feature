Feature: The System Manager can handle Systems

  This System manager is design to serve service respecting the System API

  Scenario: 01 - I can get a SystemManager
    When I create a SystemManager
    Then the SystemManager instance is not null

  Scenario: 02 - I can add a fr.snapgames.fromclasstogame.test.system.TestSystem
    When I create a SystemManager
    And I add a new "fr.snapgames.fromclasstogame.test.system.TestSystem"
    Then I can retrieve "fr.snapgames.fromclasstogame.test.system.TestSystem" from the SystemManager

  Scenario: 03 - I can remove a service
    When I create a SystemManager with a "fr.snapgames.fromclasstogame.test.system.TestSystem"
    And I remove "fr.snapgames.fromclasstogame.test.system.TestSystem"
    Then I can't get the "fr.snapgames.fromclasstogame.test.system.TestSystem" as System

  Scenario: 04 - I initialize all systems
    When I create a SystemManager
    And I add a new "fr.snapgames.fromclasstogame.test.system.TestSystem"
    And I add a new "fr.snapgames.fromclasstogame.test.system.TestSystem2"
    And I initialize SystemManager
    Then all the Systems are ready

  Scenario: 05 - at end of service, all system will be disposed.
    When I create a SystemManager
    And I add a new "fr.snapgames.fromclasstogame.test.system.TestSystem"
    And I add a new "fr.snapgames.fromclasstogame.test.system.TestSystem2"
    And I terminate the SystemManager
    Then all the systems are disposed and free
