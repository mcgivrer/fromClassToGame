package fr.snapgames.fromclasstogame.core.entity.particles;

import fr.snapgames.fromclasstogame.core.behaviors.Behavior;
import fr.snapgames.fromclasstogame.core.entity.GameObject;
import fr.snapgames.fromclasstogame.core.physic.Vector2d;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@link ParticleSystem} is a dedicated {@link GameObject} than can manage a bunch of child objects, applying the same Behavior's
 * on all its children.
 * Children object are {@link Particle} extending {@link GameObject}.
 */
public class ParticleSystem extends GameObject {

    /**
     * Specific Behaviors to be applied to children GameObject's.
     */
    public List<Behavior<Particle>> pBehave = new ArrayList<>();
    /**
     * The default number of particles managed by a System.
     */
    private int nbMaxParticle = 20;
    /**
     * A flag telling if a dead particle must be reused (restarted)
     */
    private boolean restart = false;
    /**
     * The number of particle to be emitted on each emit call.
     */
    private int feedNbParticle;
    /**
     * The delay in milliseconds between each emission of particle
     */
    private long emitFreq = 100;
    /**
     * number of emitted articles.
     */
    private long emitCpt = 0;

    /**
     * Creating anew ParticleSystem with its name adn its initial position.
     *
     * @param objectName name of the ParticleSystem to create
     * @param pos        starting position for this new ParticleSystem.
     */
    public ParticleSystem(String objectName, Vector2d pos) {
        super(objectName, pos);
        this.life = -1;
    }

    /**
     * Defining the number of particles to emit on each emit cycle.
     *
     * @param feedNbParticle number of emitted particles
     * @return the ParticleSystem (fluent API)
     */
    public ParticleSystem setFeeding(int feedNbParticle) {
        this.feedNbParticle = feedNbParticle;
        return this;
    }

    /**
     * Defining the emission frequency for this ParticleSystem
     *
     * @param f value of the delay (in milliseconds) between each emission of particle
     * @return the ParticleSystem (fluent API)
     */
    public ParticleSystem setEmitFrequency(long f) {
        this.emitFreq = f;
        return this;
    }

    /**
     * Create the nb Particle
     *
     * @param nb number of particles to be generated.
     * @return
     */
    public ParticleSystem create(int nb) {
        nbMaxParticle = nb;
        createParticles(nb);
        debugOffsetX = -100;
        return this;
    }

    /**
     * internal particle generator
     *
     * @param nb number of particles to be generated.
     */
    private void createParticles(int nb) {
        for (int i = 0; i < nb; i++) {
            Particle p = new Particle();
            onCreateParticle(p);
            child.add(p);
        }
    }

    /**
     * On particle Creation, call for any Behavior<Particle> proposing a `onCreate` implementation
     *
     * @param p particle to be initialized with Behaviors
     */
    private void onCreateParticle(Particle p) {
        if (pBehave.size() > 0) {
            pBehave.forEach(b -> b.onCreate(p));
        }
    }

    /**
     * Update process to update all particles with this ParticleSystem  Behavior<Particle>'s.
     *
     * @param dt elapsed time since previous call.
     */
    @Override
    public void update(long dt) {
        super.update(dt);
        emitCpt += dt;
        if (emitCpt > emitFreq) {
            createParticles(feedNbParticle);
            emitCpt = 0;
        }
        if (pBehave.size() > 0) {
            for (GameObject go : child) {
                Particle particle = (Particle) go;
                pBehave.forEach(b -> b.onUpdate(particle, dt));
                if (particle.life <= 0 && getRestart()) {
                    onCreateParticle(particle);
                }
            }
        }

    }

    /**
     * Add a new Behavior<Particle> tio ths ParticleSystem.
     *
     * @param bp the behavior to be added.
     * @return
     */
    public ParticleSystem addParticleBehavior(Behavior<Particle> bp) {
        pBehave.add(bp);
        return this;
    }

    /**
     * Generate specific debug information for a ParticleSystem.
     *
     * @return lines of debug information
     */
    @Override
    public List<String> getDebugInfo() {
        List<String> ls = super.getDebugInfo();
        ls.add(String.format("npl:%d", this.nbMaxParticle));
        return ls;
    }

    /**
     * Get the restart flag state.
     *
     * @return
     */
    public boolean getRestart() {
        return restart;
    }

    /**
     * Set the restart flag.
     *
     * @param b
     */
    public void setRestart(boolean b) {
        this.restart = b;
    }
}
