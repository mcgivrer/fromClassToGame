Feature: An EntityPool is a managed fixed size Entity array

  The EntityPool allow to create and maintain a pool of entity, initialized at a fixed size
  and retrieve inactive ones.

  Scenario: 01 - Create an Entity Pool
    Given An EntityPoolManager is created
    And I create an EntityPool named "particles" with size of 200
    And I add the EntityPool named "particles"
    Then I get the EntityPool named "particles" with 200 available items

  Scenario: 02 - remove an EntityPool
    Given An EntityPoolManager is created with EntityPools "test1,test2,test3"
    And I remove the EntityPool "test1"
    Then the EntityPoolManager has no more "test1"

  Scenario: 03 - Add an object to an. EntityPool
    Given An EntityPoolManager is created with EntityPools "test1"
    And I add a GameObject "TestGO" to the EntityPool "test1"
    Then the EntityPool "test1" contains the GameObject "TestGO"

  Scenario: 04 - Game has a an EPM
    Given the Game is started with config "no-scene"
    Then The Game has an EntityPoolManager System

  Scenario: 05 - Game has a default EntityPool for GameObject
    Given the Game is started with config "no-scene"
    Then The Game has an EntityPool for GameObject