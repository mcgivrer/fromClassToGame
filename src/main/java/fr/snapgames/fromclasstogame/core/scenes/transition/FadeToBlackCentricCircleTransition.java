package fr.snapgames.fromclasstogame.core.scenes.transition;

import fr.snapgames.fromclasstogame.core.gfx.Render;
import fr.snapgames.fromclasstogame.core.physic.Utils;
import fr.snapgames.fromclasstogame.core.scenes.Scene;

import java.awt.*;

/**
 * a transition between 2 scenes with black concentric circles.
 */
public class FadeToBlackCentricCircleTransition extends AbstractSceneTransition implements Transition<Render, Scene> {


    private final Render render;
    private float ratio;

    public FadeToBlackCentricCircleTransition(Render render, long duration, float ratio) {
        super();
        this.render = render;
        this.duration = duration;
        this.ratio = ratio;
    }

    @Override
    public void process(Render render, Scene start, Scene end) {
        float threshold = 0.0f;
        // TODO compute the right ratio to set the right colorTransparency
        // fade to black and switch to new scene, and fade up to light
        if (end != null) {
            threshold = 0.5f;
        } else {
            threshold = 1.0f;
        }
        if (end != null && getTime() < duration * threshold) {
            render.render(end);
            float colorTransparency = (threshold / (ratio - getTime()));
            render.fillRectangle(0, 0,
                    render.getBuffer().getWidth(),
                    render.getBuffer().getHeight(),
                    new Color(0.0f, 0.0f, 0.0f, colorTransparency));
        }
    }
}
