package fr.snapgames.fromclasstogame.core.entity.particles;

import fr.snapgames.fromclasstogame.core.behaviors.Behavior;
import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.physic.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class ParticleSystem extends GameObject {

    public List<Behavior<Particle>> pBehave = new ArrayList<>();
    public List<Particle> particles = new ArrayList<>();
    private int nbMaxParticle = 20;
    private boolean restart = false;
    private int feedNbParticle;
    private long emitFreq = 100;
    private long emitCpt = 0;

    public ParticleSystem(String objectName, Vector2d pos) {
        super(objectName, pos);
        this.life = -1;
    }

    public ParticleSystem setFeeding(int feedNbParticle) {
        this.feedNbParticle = feedNbParticle;
        return this;
    }

    public ParticleSystem setEmitFrequency(long f) {
        this.emitFreq = f;
        return this;
    }

    public ParticleSystem create(int nb) {
        nbMaxParticle = nb;
        createParticles(nb);
        return this;
    }

    private void createParticles(int nb) {
        for (int i = 0; i < nb; i++) {
            Particle p = new Particle();
            onCreateParticle(p);
            particles.add(p);
        }
    }

    private void onCreateParticle(Particle p) {
        if (pBehave.size() > 0) {
            pBehave.forEach(b -> b.onCreate(p));
        }
    }

    @Override
    public void update(long dt) {
        super.update(dt);
        emitCpt += dt;
        if (emitCpt > emitFreq) {
            createParticles(feedNbParticle);
            emitCpt = 0;
        }
        if (pBehave.size() > 0) {
            for (Particle particle : particles) {
                pBehave.forEach(b -> b.onUpdate(particle, dt));
                if (particle.life <= 0 && getRestart()) {
                    onCreateParticle(particle);
                }
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
        ls.add(String.format("NbPtl:" + this.nbMaxParticle));
        return ls;
    }

    public boolean getRestart() {
        return restart;
    }

    public void setRestart(boolean b) {
        this.restart = b;
    }
}
