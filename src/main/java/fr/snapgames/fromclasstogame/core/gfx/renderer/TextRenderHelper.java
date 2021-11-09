package fr.snapgames.fromclasstogame.core.gfx.renderer;

import java.awt.Graphics2D;

import fr.snapgames.fromclasstogame.core.entity.TextObject;

public class TextRenderHelper implements RenderHelper<TextObject> {

    @Override
    public void draw(Graphics2D g, TextObject to) {
        g.setFont(to.font);
        g.setColor(to.color);
        g.drawString(to.text, (int) (to.position.x), (int) (to.position.y));
    }

    @Override
    public String getType() {
        return TextObject.class.getName();
    }

}
