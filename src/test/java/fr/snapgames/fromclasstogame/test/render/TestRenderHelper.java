package fr.snapgames.fromclasstogame.test.render;

import fr.snapgames.fromclasstogame.core.gfx.renderer.RenderHelper;
import fr.snapgames.fromclasstogame.test.entity.TestObject;

import java.awt.*;

public class TestRenderHelper implements RenderHelper {

    @Override
    public String getType() {
        return TestObject.class.getName();
    }

    @Override
    public void draw(Graphics2D g, Object go) {
        TestObject to = (TestObject) go;
        to.setFlag(true);
    }
}
