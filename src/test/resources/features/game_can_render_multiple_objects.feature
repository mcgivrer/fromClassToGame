Feature: Render and the Render Helpers

  The Game has a Rendering pipeline with an adaptive way of rendering current and future Entity implementation.

  Scenario: The Render has default RenderHelper
    Given The Game start with default config
    Then the Render is ready
    And the RenderHelper for "GameObject" is ready
    And the RenderHelper for "TextObject" is ready

