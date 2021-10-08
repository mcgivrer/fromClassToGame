package fr.snapgames.fromclasstogame.demo.render;

import fr.snapgames.fromclasstogame.demo.entity.TextValueObject;

import java.awt.*;

public class TextValueRenderHelper extends ScoreRenderHelper {

    @Override
    public void draw(Graphics2D g, Object o) {
        double maxBorderWidth = 1;
        TextValueObject so = (TextValueObject) o;
        g.setColor(so.color);
        g.setFont(so.font);
        drawBorder(g, maxBorderWidth, so);
        g.setColor(so.color);
        g.drawString(so.text, (int) (so.x), (int) (so.y));
    }

    @Override
    public String getType() {
        return TextValueObject.class.getName();
    }

}
