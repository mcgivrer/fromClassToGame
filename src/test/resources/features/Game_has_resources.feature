Feature: A Game has resources

  The Game can read resources like mage, font, etc...

  Scenario: Reading some Font resource
    Given the Game is instantiated
    And the Game is running
    And the resources are cleared
    And The font "fonts/FreePixel.ttf" is added
    Then the ResourceManager has 1 resources

  Scenario: Reading some Image resource
    Given the Game is instantiated
    And the Game is running
    And the resources are cleared
    And The image "images/tiles.png" is added
    Then the ResourceManager has 1 resources

  Scenario: Reading some unknown resource
    Given the Game is instantiated
    And the Game is running
    And the resources are cleared
    And The image "nothing_to_be_found_as_resource" is added
    Then the ResourceManager has 0 resources

  Scenario: Read and Slice an image resource
    Given the Game is instantiated
    And the Game is running
    And the resources are cleared
    And The image "images/tiles.png" as "player" is sliced at (0,0) sizing (16,16)
    Then the ResourceManager has 1 resources
    And The resulting "images/tiles.png:player" image sizing (16,16)
