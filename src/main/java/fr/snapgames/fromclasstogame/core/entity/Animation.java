package fr.snapgames.fromclasstogame.core.entity;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Animation {
    Map<String, BufferedImage[]> frames = new HashMap<>();
    int animationFrame = 0;
    String currentAnimation = "";
    BufferedImage[] currentFrames;
    final double frameDuration = 1000.0 / 25.0;
    double timeCount = 0.0;


    public Animation() {

    }


    public void setCurrentAnimation(String caf) {
        currentAnimation = caf;
    }

    public void setAnimationFrame(int af) {
        animationFrame = af;
    }

    public BufferedImage[] getAnimationFrames(String key) {
        return frames.get(key);
    }

    public void addFrames(String animationKey, BufferedImage[] imageFrames) {
        frames.put(animationKey, imageFrames);
    }

    public void update(double elapsed) {
        timeCount += elapsed;
        if (timeCount > frameDuration) {
            timeCount = 0;
            animationFrame++;
            if (animationFrame > currentFrames.length) {
                animationFrame = 0;
            }
        }
    }

    public BufferedImage getCurrentFrame() {
        return frames.get(currentAnimation)[animationFrame];
    }

    public void reset() {
        animationFrame = 0;
        timeCount = 0;
    }

    public String[] getAnimationKeys() {
        return frames.keySet().toArray(new String[0]);
    }
}
