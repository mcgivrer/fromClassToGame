Feature: A Scene can have behaviors

  A simple scene can have one or more behaviors and those behavior have 4 events.

  Scenario: 01 - A Scene Have no default behavior
    Given A Scene "Test1" is created
    Then the Scene "Test1" has no behavior
