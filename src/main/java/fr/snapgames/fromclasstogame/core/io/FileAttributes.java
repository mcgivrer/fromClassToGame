package fr.snapgames.fromclasstogame.core.io;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

        List<String> f = ResourceManager.getFile(fileName);
        String line = "", l = "";
        int i = 0;
        while (i < f.size()) {
            l = f.get(i);
            if (!l.startsWith("#")) {
                if (l.endsWith("\\")) {
                    while (l.endsWith("\\")) {
                        line += l.substring(0, l.length() - 1).trim();
                        i++;
                        l = f.get(i);
                    }
                    line += l.trim();
                } else {
                    line = l;
                }
                String[] values = line.split(":");
                attributes.put(values[0], values[1]);
                line = "";
            }
            i++;
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
        for (String s : subAttrs) {
            String[] vals = s.split("=");
            subAttrsMap.put(vals[0], vals[1]);
        }
        return subAttrsMap.get(subKey);
    }

    public List<String> find(String s) {
        List<String> attrs = attributes.keySet().stream().filter(f -> f.startsWith(s)).collect(Collectors.toList());
        return attrs;
    }
}
