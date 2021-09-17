Feature: The System Manager can handle Systems

  This System manager is design to serve service respecting the System API

  Scenario: I can get a SystemManager
    When I create a SystemManager
    Then the SystemManager instance is not null

  Scenario: I can add a features.system.TestSystem
    When I create a SystemManager
    And I add a new "features.system.TestSystem"
    Then I can retrieve "features.system.TestSystem" from the SystemManager

  Scenario: I can remove a service
    When I create a SystemManager with a "features.system.TestSystem"
    And I remove "features.system.TestSystem"
    Then I can't get the "features.system.TestSystem" as System

  Scenario: I initialize all systems
    When I create a SystemManager
    And I add a new "features.system.TestSystem"
    And I add a new "features.system.TestSystem2"
    And I initialize SystemManager
    Then all the Systems are ready.

  Scenario: at end of service, all system will be disposed.
    When I create a SystemManager
    And I add a new "features.system.TestSystem"
    And I add a new "features.system.TestSystem2"
    And I terminate the SystemManager
    Then all the systems are disposed and free
