package fr.snapgames.fromclasstogame.core.entity.particles;

import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.physic.Vector2d;

import java.util.HashMap;
import java.util.Map;

public class Particle extends GameObject {

    public Map<String, Object> attributes = new HashMap<>();

    public Particle() {
        this(0);
    }

    public Particle(long life) {
        super("particle_", null);
        this.name = this.name + id;
        this.life = life;
    }

    public Particle setSize(double size) {
        this.width = this.height = size;
        return this;
    }

    public Particle setPosition(Vector2d pos) {
        this.position = pos;
        return this;
    }

    public Particle setVelocity(Vector2d vel) {
        this.velocity = vel;
        return this;
    }

    public Particle setAcceleration(Vector2d acc) {
        this.acceleration = acc;
        return this;
    }

    @Override
    public void update(long dt) {
        this.life--;
    }

}
