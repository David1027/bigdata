package org.start2do.utils.io;

import java.io.IOException;
import java.io.InputStream;

public interface InputStreamSource {
  InputStream getInputStream() throws IOException;
}
