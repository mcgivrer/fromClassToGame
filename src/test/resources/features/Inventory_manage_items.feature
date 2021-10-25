Feature: The InventoryObject intends to manage a limited set of items

  The player, during its game, can collect items and use them. The InventoryObject role consists
  in managing this set and select the active one through keyboard interaction.

  Scenario: The Inventory has limited number of placeholder
    Given an InventoryObject "inventory" is created
    Then the number of item placeholder is set to 1.

  Scenario: add an object to the inventory
    Given an InventoryObject "my_inventory" is created
    And I create a new GameObject "item1" with an attribute "inventory"
    And I add the GameObject "item1" to InventoryObject "my_inventory"
    Then the InventoryObject "my_inventory" has 1 Item.
