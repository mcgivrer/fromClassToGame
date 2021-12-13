package fr.snapgames.fromclasstogame.core.gfx.renderer;

import fr.snapgames.fromclasstogame.core.entity.TextObject;
import fr.snapgames.fromclasstogame.core.gfx.Render;

import java.awt.*;

public class TextRenderHelper extends AbstractRenderHelper implements RenderHelper<TextObject> {

    public TextRenderHelper(Render r) {
        super(r);
    }

    @Override
    public void draw(Graphics2D g, TextObject to) {
        setFont(g, to.font);
        drawTextBorder(g, 2, to);
        setColor(g, to.color);
        drawText(g, to.text, to.position);
        to.width = g.getFontMetrics().stringWidth(to.text);
        to.height = g.getFontMetrics().getHeight();
    }

    @Override
    public String getType() {
        return TextObject.class.getName();
    }

    @Override
    public void drawDebugInfo(Graphics2D g, TextObject go) {
        super.drawDebugInfo(g, go);
    }


}
