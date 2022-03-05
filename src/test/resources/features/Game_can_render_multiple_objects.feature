Feature: Render and the Render Helpers

  The Game has a Rendering pipeline with an adaptive way of rendering current and future Entity implementation.

  @Render @RenderHelper
  Scenario: 01 - The Render has default RenderHelper
    Given The Game start with default config
    Then the Render is ready
    And the RenderHelper for "fr.snapgames.fromclasstogame.core.entity.GameObject" is ready
    And the RenderHelper for "fr.snapgames.fromclasstogame.core.entity.TextObject" is ready

  @Render @RenderHelper
  Scenario: 02 - I can add a new RenderHelper
    Given The Game start with default config
    Given the Render is ready
    And I activate the scene "testrender"
    And I add a TestObject named "test"
    And I add a new RenderHelper "fr.snapgames.fromclasstogame.test.render.TestRenderHelper" for a "fr.snapgames.fromclasstogame.test.entity.TestObject"
    Then the RenderHelper for "fr.snapgames.fromclasstogame.test.entity.TestObject" is ready
    And the TestObject named "test" is rendered.

  @Render @TextObject
  Scenario: 03 - I can render a TextObject
    Given the Game is instantiated with config "test-render"
    And the Game is running
    And I activate the scene "testrender"
    And I add a TextObject named "to01"
    Then the GameObject named "to01" is rendered.
