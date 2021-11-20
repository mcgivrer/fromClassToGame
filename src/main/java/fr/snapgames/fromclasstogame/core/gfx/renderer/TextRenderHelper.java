package fr.snapgames.fromclasstogame.core.gfx.renderer;

import java.awt.*;

import fr.snapgames.fromclasstogame.core.entity.TextObject;

public class TextRenderHelper extends AbstractRenderHelper implements RenderHelper<TextObject> {
    private Color shadowColor = new Color(0.2f,0.2f,0.2f,0.6f);

    @Override
    public void draw(Graphics2D g, TextObject to) {
        drawTextBorder(g,2,to);
        setColor(g, to.color);
        drawText(g,to.text,to.position);
    }

    @Override
    public String getType() {
        return TextObject.class.getName();
    }

}
