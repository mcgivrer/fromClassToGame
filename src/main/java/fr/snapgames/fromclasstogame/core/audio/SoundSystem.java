/**
 * SnapGames
 *
 * @see https://github.com/mcgivrer/bgf/
 * @since 2018
 */
package fr.snapgames.fromclasstogame.core.audio;

import com.google.gson.Gson;
import fr.snapgames.fromclasstogame.core.Game;
import fr.snapgames.fromclasstogame.core.config.Configuration;
import fr.snapgames.fromclasstogame.core.io.ResourceManager;
import fr.snapgames.fromclasstogame.core.system.System;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.AudioFileFormat.Type;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer.Info;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class is intend to manage and control Sound play and output.
 *
 * @author Frédéric Delorme.
 */
public class SoundSystem extends System {


    /**
     * Max number of SoundClip to be stored in cache.
     */
    private static final int MAX_SOUNDS_IN_STACK = 40;
    private final Logger log = LoggerFactory.getLogger(SoundSystem.class);
    /**
     * Internal play Stack
     */
    private Stack<String> soundsStack = new Stack<String>();
    /**
     * Internal SoundBank.
     */
    private Map<String, SoundClip> soundBank = new ConcurrentHashMap<String, SoundClip>();

    /**
     * mute the full SoundSystem.
     */
    private boolean mute = false;

    /**
     * current sound volume for SoundSystem.
     */
    private float soundVolume;

    /**
     * current music volume for SoundSystem.
     */
    private float musicVolume;

    private Game game;

    /**
     * Internal constructor.
     */
    public SoundSystem(Game game) {
        super(game);
    }

    /**
     * Load a Sound from <code>filename</code> to the sound bank.
     *
     * @param filename file name of the sound to be loaded to the
     *                 <code>soundBank</code>.
     * @return filename if file has been loaded into the sound bank or null.
     * {@link SoundSystem}
     * <p>
     * SoundClip coinClip =
     * (SoundClip)ResourceManager.get("res/audio/sounds/135936__bradwesson__collectcoin.wav");
     * SystemManager.get(SoundSystem.class).add("coin", coinClip);
     * </p>
     * .
     */
    public String load(String code, String filename) {
        if (!soundBank.containsKey(code) && !mute) {
            SoundClip sc = ResourceManager.getSoundClip(filename);
            if (sc != null) {
                soundBank.put(code, sc);
                log.debug("Load sound {} to sound bank with code {}", filename, code);
            }
            return filename;
        } else {
            return null;
        }
    }

    /**
     * Add the {@link SoundClip} to the sounds bank withe <code>code</code> as
     * identifier.
     *
     * @param code the code to identify the SoundClip in the bank
     * @param sc   the {@link SoundClip} top be added.
     * @return
     */
    public String add(String code, SoundClip sc) {
        if (sc != null && !mute) {
            soundBank.put(code, sc);
            log.debug("Load sound {} to Sound Bank", sc.getCode(), code);
        }
        return code;
    }

    /**
     * play the sound with <code>code</code>
     *
     * @param code internal code of the sound to be played.
     */
    public void play(String code) {
        if (!mute) {
            if (soundBank.containsKey(code)) {
                SoundClip sc = soundBank.get(code);
                sc.play();
                log.debug("Play sound {}", code);
            } else {
                log.error("unable to find the sound {} in the SoundBank !", code);
            }
        } else {
            log.debug("Mute mode activated, {} not played", code);
        }
    }

    /**
     * play the sound with <code>code</code>
     *
     * @param code   internal code of the sound to be played.
     * @param volume volume level to be played.
     */
    public void play(String code, float volume) {
        if (!mute) {
            if (soundBank.containsKey(code)) {
                SoundClip sc = soundBank.get(code);
                sc.play(0.5f, volume * soundVolume);
                log.debug("Play sound {} with volume {}", code, volume);
            } else {
                log.error("unable to find the sound {} in the SoundBank !", code);
            }
        } else {
            log.debug("Mute mode activated, {} not played", code);
        }
    }

    /**
     * play the sound with <code>code</code>
     *
     * @param code   internal code of the sound to be played.
     * @param volume volume level to be played.
     * @param pan    the pan for the sound to be played.
     */

    public void play(String code, float volume, float pan, boolean loop) {
        if (!mute) {

            if (soundBank.containsKey(code)) {
                SoundClip sc = soundBank.get(code);
                if (loop && sc != null) {
                    sc.play(pan, volume);
                    sc.loop();
                }
                log.debug("Play sound {} with volume {} and pan {}", code, volume, pan);
            } else {
                log.error("unable to find the sound {} in the SoundBank !", code);
            }
        } else {
            log.debug("Mute mode activated, {} not played", code);
        }
    }

    public void play(String code, float volume, float pan) {
        play(code, volume, pan, false);
    }

    public void loop(String code, float volume) {
        play(code, volume, 0.5f, true);
    }

    /**
     * Is the sound code playing right now ?
     *
     * @param code code of the sound to test.
     * @return
     */
    public boolean isPlaying(String code) {
        if (soundBank.containsKey(code)) {
            return soundBank.get(code).isPlaying();
        } else {
            return false;
        }
    }

    public boolean isMute() {
        return mute;
    }

    public void setMute(boolean mute) {
        this.mute = mute;
    }

    @Override
    public String getName() {
        return this.getClass().getCanonicalName();
    }

    @Override
    public int initialize(Configuration config) {
        soundsStack.setSize(MAX_SOUNDS_IN_STACK);
        log.info("Initialize SoundControl with {} stack places", MAX_SOUNDS_IN_STACK);
        Type[] supportedFiletypes = AudioSystem.getAudioFileTypes();
        for (Type t : supportedFiletypes) {
            log.info("supported file format '{}'", t);
        }
        this.mute = config.mute;

        this.soundVolume = config.soundVolume;
        this.musicVolume = config.musicVolume;
        Info[] infos = AudioSystem.getMixerInfo();
        for (Info info : infos) {
            Gson gson = new Gson();
            log.info("Mixer info:{}", gson.toJson(info));
        }
        return 0;
    }

    @Override
    public void dispose() {

    }
}