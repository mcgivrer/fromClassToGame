Feature: A Game has resources

  The Game can read resources like mage, font, etc...

  @ResourceManager @RM_Font
  Scenario: 01 - Reading some Font resource
    Given the Game is instantiated
    And the Game is running
    And the resources are cleared
    And The font "fonts/FreePixel.ttf" is added
    Then the ResourceManager has 1 resources

  @ResourceManager @RM_Image
  Scenario: 02 - Reading some Image resource
    Given the Game is instantiated
    And the Game is running
    And the resources are cleared
    And The image "images/tiles01.png" is added
    Then the ResourceManager has 1 resources

  @ResourceManager @RM_Image
  Scenario: 03 - Reading some unknown resource
    Given the Game is instantiated
    And the Game is running
    And the resources are cleared
    And The image "nothing_to_be_found_as_resource" is added
    Then the ResourceManager has 0 resources

  @ResourceManager @RM_Image
  Scenario: 04 - Read and Slice an image resource
    Given the Game is instantiated
    And the Game is running
    And the resources are cleared
    And The image "images/tiles01.png" as "player" is sliced at (0,0) sizing (16,16)
    Then the ResourceManager has 1 resources
    And The resulting "images/tiles01.png:player" image sizing (16,16)
