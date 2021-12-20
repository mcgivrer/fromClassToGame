Feature: A Scene can have behaviors

  A simple scene can have one or more behaviors and those behavior have 4 events.

  @Scene @Behavior
  Scenario: 01 - A Scene Have no default behavior
    Given A Scene "Test1" is created
    Then the Scene "Test1" has no behavior

  @Scene @Behavior
  Scenario: 02 - I can add a behavior to a Scene
    Given A Scene "Test1" is created
    And I add a Behavior TestBehavior to the scene "Test1"
    Then the Scene "Test1" has 1 behavior
