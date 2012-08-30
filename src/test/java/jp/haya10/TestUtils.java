package jp.haya10;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Utility for Test.
 */
public final class TestUtils {

    private TestUtils() {
        // no operation
    }

    /**
     * Get script file.
     *
     * @param clazz target class.
     * @param name target name.
     * @return script file.
     */
    public static String getScriptFile(Class<?> clazz, String name) {
        String html = "/" + clazz.getCanonicalName().replace('.', '/') + name + ".html";
        URL resource = clazz.getResource(html);
        if (resource == null)
            throw new RuntimeException(new FileNotFoundException());
        try {
            return new File(resource.toURI()).getCanonicalPath();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
