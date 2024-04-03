package filelogic;

import java.io.IOException;

public interface BaseReader {
    void readFromPath(String path) throws IOException;
}
