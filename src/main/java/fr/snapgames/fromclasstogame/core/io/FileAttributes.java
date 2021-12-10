package fr.snapgames.fromclasstogame.core.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * File Attributes reader.
 *
 * @author Frédéric Delorme
 * @since 1.0.1
 */
public class FileAttributes {
    private static final Logger logger = LoggerFactory.getLogger(FileAttributes.class);

    private static Map<String, String> attributes = new HashMap<>();

    /**
     * Read the file filename into a Map of attributes.
     *
     * @param fileName
     */
    public static FileAttributes read(String fileName) {
        FileAttributes fa = new FileAttributes();
        String strLevelsPath = FileAttributes.class.getClassLoader().getResource("/").getPath().substring(1);
        List<String> f = ResourceManager.getFile(fileName);
        for (String l : f) {
            String[] values = l.split(":");
            attributes.put(values[0], values[1]);
        }
        return fa;
    }

    /**
     * Retrieve the value for attribute key.
     *
     * @param key
     * @return
     */
    public String get(String key) {
        assert (attributes != null);
        return attributes.get(key);
    }

    public String getSubAttribute(String key, String subKey) {
        assert (attributes != null);
        String[] subAttrs = attributes.get(key).split(";");
        Map<String, String> subAttrsMap = new HashMap<>();
        subAttrsMap = (Map<String, String>) Arrays.stream(subAttrs).flatMap(s -> {
            return Stream.of(s.split("="));
        });
        return subAttrsMap.get(subKey);
    }

    public List<String> find(String s) {
        List<String> attrs = attributes.keySet().stream().filter(f -> f.startsWith(s)).collect(Collectors.toList());
        return attrs;
    }
}
