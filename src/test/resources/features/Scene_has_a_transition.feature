Feature: A Scene can have an in and an out transition.

  A Transition, a specific rendering processor, can be set an in and out of Scene. At Scene `activation`, the `out`
  transition of the previous scene is activated at the same time, the `in` transition of the new Scene is activated.

  Scenario: I can add an `in` transition to a Scene
    Given a new Scene is created
    Then I can has an IN transition

  Scenario: I can add an `out` transition to a Scene
    Given a new Scene is created
    Then I can add an OUT transition
