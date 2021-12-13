package fr.snapgames.fromclasstogame.core.io;

import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

/**
 * A simple international Message helper to retrieve the right message on its key in the active language.
 *
 * @author Frédéric Delorme
 * @since 1.0.2
 */
public class I18n {
    /**
     * Resource bunde containing translated files.
     */
    private static final ResourceBundle messages = ResourceBundle.getBundle("i18n.messages");

    /**
     * retrieve the message for key
     *
     * @param key key of the message to retrieve from translated file.
     * @return
     */
    public static String getMessage(String key) {
        return messages.getString(key);
    }

    /**
     * Retrieve the message for the key, and compose it with values
     *
     * @param key    key of the message to retrieve from translated file.
     * @param values to be replaced into the message template.
     * @return
     */
    public static String getMessage(String key, String... values) {
        return String.format(messages.getString(key), values);
    }

}
