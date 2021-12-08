package fr.snapgames.fromclasstogame.core.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URISyntaxException;

/**
 * File utilities to manage path and operating system differences.
 *
 * @author Frédéric Delorme
 * @since 1.0.2
 */
public class FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * Returns the absolute path of the current directory in which the given
     * class
     * file is.
     *
     * @param classs
     * @return The absolute path of the current directory in which the class
     * file is.
     * @author GOXR3PLUS[StackOverFlow user] + bachden [StackOverFlow user]
     * @link https://stackoverflow.com/questions/320542/how-to-get-the-path-of-a-running-jar-file#answer-44071072
     */
    public static final String getBasePathForClass(Class<?> classs) {

        // Local variables
        File file;
        String basePath = "";
        boolean failed = false;

        // Let's give a first try
        try {
            file = new File(classs.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());

            if (file.isFile() || file.getPath().endsWith(".jar") || file.getPath().endsWith(".zip")) {
                basePath = file.getParent();
            } else {
                basePath = file.getPath();
            }
        } catch (URISyntaxException ex) {
            failed = true;
            logger.error(
                    "Cannot figure out base path for class with way (1): ", ex);
        }

        // The above failed?
        if (failed) {
            try {
                file = new File(classs.getClassLoader().getResource("").toURI().getPath());
                basePath = file.getAbsolutePath();

                // the below is for testing purposes...
                // starts with File.separator?
                // String l = local.replaceFirst("[" + File.separator +
                // "/\\\\]", "")
            } catch (URISyntaxException ex) {
                logger.error("Cannot figure out base path for class with way (2): ", ex);
            }
        }

        // fix to run inside eclipse
        if (basePath.endsWith(File.separator + "lib") || basePath.endsWith(File.separator + "bin")
                || basePath.endsWith("bin" + File.separator) || basePath.endsWith("lib" + File.separator)) {
            basePath = basePath.substring(0, basePath.length() - 4);
        }
        // fix to run inside netbeans
        if (basePath.endsWith(File.separator + "build" + File.separator + "classes")) {
            basePath = basePath.substring(0, basePath.length() - 14);
        }
        // end fix
        if (!basePath.endsWith(File.separator)) {
            basePath = basePath + File.separator;
        }
        return basePath;
    }
}
