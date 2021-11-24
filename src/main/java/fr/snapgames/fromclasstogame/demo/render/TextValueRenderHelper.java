package fr.snapgames.fromclasstogame.demo.render;

import fr.snapgames.fromclasstogame.core.entity.TextObject;
import fr.snapgames.fromclasstogame.core.gfx.renderer.AbstractRenderHelper;
import fr.snapgames.fromclasstogame.core.gfx.renderer.RenderHelper;
import fr.snapgames.fromclasstogame.demo.entity.TextValueObject;

import java.awt.*;

public class TextValueRenderHelper extends AbstractRenderHelper implements RenderHelper<TextValueObject> {

    @Override
    public void draw(Graphics2D g, TextValueObject tvo) {
        double maxBorderWidth = 1;
        g.setColor(tvo.color);
        g.setFont(tvo.font);
        drawTextBorder(g, maxBorderWidth, (TextObject) tvo);
        g.setColor(tvo.color);
        g.drawString(tvo.text, (int) (tvo.position.x), (int) (tvo.position.y));
    }

    @Override
    public String getType() {
        return TextValueObject.class.getName();
    }

    @Override
    public void drawDebugInfo(Graphics2D g, TextValueObject go) {
        super.drawDebugInfo(g, go);
    }
}
