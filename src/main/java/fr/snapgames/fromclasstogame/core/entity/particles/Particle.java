package fr.snapgames.fromclasstogame.core.entity.particles;

import fr.snapgames.fromclasstogame.core.entity.EntityUpdate;
import fr.snapgames.fromclasstogame.core.physic.Vector2d;

import java.util.HashMap;
import java.util.Map;

public class Particle implements EntityUpdate {
    public Vector2d position;
    public Vector2d velocity;
    public Vector2d acceleration;
    public double size;

    public int life = 0;
    public boolean alive;

    public Map<String, Object> attributes = new HashMap<>();

    public Particle() {
        this(0);
    }

    public Particle(int life) {
        this.life = life;
    }

    public Particle setSize(double size) {
        this.size = size;
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
