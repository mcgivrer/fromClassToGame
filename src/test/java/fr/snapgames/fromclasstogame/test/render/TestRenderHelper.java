package fr.snapgames.fromclasstogame.test.render;

import fr.snapgames.fromclasstogame.core.gfx.Renderer;
import fr.snapgames.fromclasstogame.core.gfx.renderer.AbstractRenderHelper;
import fr.snapgames.fromclasstogame.core.gfx.renderer.RenderHelper;
import fr.snapgames.fromclasstogame.test.entity.TestObject;

import java.awt.*;

public class TestRenderHelper extends AbstractRenderHelper implements RenderHelper<TestObject> {

    public TestRenderHelper(Renderer r) {
        super(r);
    }

    @Override
    public String getType() {
        return TestObject.class.getName();
    }

    @Override
    public void draw(Graphics2D g, TestObject go) {

        go.setFlag(true);
    }

    @Override
    public void drawDebugInfo(Graphics2D g, TestObject go) {
        super.drawDebugInfo(g, go);
    }
}
