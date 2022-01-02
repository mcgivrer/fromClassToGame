Feature: A Transition is an way to animate the transition between 2 scene at Scene activation time.

  While defining a new Transition, some parameters are needed to process there own management.

  Scenario: I create a new Transition
    Given a Scene "test" is instantiated
    And a Transition "testIN" is added to Scene "test"
    And I set the duration time to 1500 ms and the IN waiting time to 500 ms
    Then the Transition "testIN" is executed
    And the waiting IN time is 500 ms
    And The played transition IN duration is 1500
