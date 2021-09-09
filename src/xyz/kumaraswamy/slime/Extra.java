package xyz.kumaraswamy.slime;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Extra {
    public static char[] read(final Object data) throws IOException {
        if (data instanceof String) {
            return ((String) data).toCharArray();
        }
        return new String(Files.readAllBytes(((File) data).toPath())).toCharArray();
    }
}
