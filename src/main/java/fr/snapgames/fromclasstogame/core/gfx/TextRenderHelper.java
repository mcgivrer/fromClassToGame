package fr.snapgames.fromclasstogame.core.gfx;

import java.awt.Graphics2D;

import fr.snapgames.fromclasstogame.core.entity.TextObject;

public class TextRenderHelper implements RenderHelper {

    @Override
    public void draw(Graphics2D g, Object go) {
        TextObject to = (TextObject) go;
        g.setFont(to.font);
        g.drawString(to.text, (int) (to.x), (int) (to.y));

    }

    @Override
    public String getType() {
        return TextObject.class.getName();
    }

}
