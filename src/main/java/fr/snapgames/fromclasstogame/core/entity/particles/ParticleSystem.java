package fr.snapgames.fromclasstogame.core.entity.particles;

import fr.snapgames.fromclasstogame.core.behaviors.Behavior;
import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.physic.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class ParticleSystem extends GameObject {

    public List<Behavior<Particle>> pBehave = new ArrayList<>();
    public List<Particle> particles = new ArrayList<>();
    int nbMaxParticle = 20;

    public ParticleSystem(String objectName, Vector2d pos) {
        super(objectName, pos);
    }


    public ParticleSystem create(int nb) {
        nbMaxParticle = nb;
        for (int i = 0; i < nbMaxParticle; i++) {
            Particle p = new Particle();
            if (pBehave.size() > 0) {
                pBehave.forEach(b -> b.onCreate(p));
            }
            particles.add(p);
        }
        return this;
    }

    @Override
    public void update(long dt) {
        super.update(dt);
        if (pBehave.size() > 0) {
            for (Particle particle : particles) {
                pBehave.forEach(b -> b.onUpdate(particle, dt));
            }
        }
    }

    public ParticleSystem addParticleBehavior(Behavior<Particle> bp) {
        pBehave.add(bp);
        return this;
    }

    @Override
    public List<String> getDebugInfo() {
        List<String> ls = super.getDebugInfo();
        ls.add(String.format("NbPtl:"+this.nbMaxParticle));
        return ls;
    }
}
