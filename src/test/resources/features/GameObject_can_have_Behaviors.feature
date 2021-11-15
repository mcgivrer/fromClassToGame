Feature: A GameObject instance can have one or more Behavior's.

  Any game object can have its default behavior altered by adding new processing
  through some Behavior implementation.

  Scenario: 01 - A GameObject have no Behavior after creation.
    Given a new GameObject named "behaved" at (0.0,0.0)
    Then the GameObject "behaved" has no behavior.

  Scenario: 02 - A GameObject can have a TestBehavior.
    Given a new GameObject named "behaved" at (0.0,0.0)
    And I add a TestBehavior to the GameObject "behaved"
    Then the GameObject "behaved" has 1 behavior(s)



