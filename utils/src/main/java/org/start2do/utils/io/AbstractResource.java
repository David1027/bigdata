package org.start2do.utils.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import org.start2do.utils.ResourceUtils;

public abstract class AbstractResource implements Resource {

  @Override
  public boolean exists() {
    try {
      return this.getFile().exists();
    } catch (IOException var4) {
      try {
        this.getInputStream().close();
        return true;
      } catch (Throwable var3) {
        return false;
      }
    }
  }

  @Override
  public boolean isReadable() {
    return this.exists();
  }

  @Override
  public boolean isOpen() {
    return false;
  }

  @Override
  public boolean isFile() {
    return false;
  }

  @Override
  public URL getURL() throws IOException {
    throw new FileNotFoundException(this.getDescription() + " cannot be resolved to URL");
  }

  @Override
  public URI getURI() throws IOException {
    URL url = this.getURL();
    try {
      return ResourceUtils.toURI(url);
    } catch (URISyntaxException var3) {
      throw new IOException("Invalid URI [" + url + "]", var3);
    }
  }

  @Override
  public File getFile() throws IOException {
    throw new FileNotFoundException(
        this.getDescription() + " cannot be resolved to absolute file path");
  }

  @Override
  public ReadableByteChannel readableChannel() throws IOException {
    return Channels.newChannel(this.getInputStream());
  }

  @Override
  public long contentLength() throws IOException {
    InputStream is = this.getInputStream();

    try {
      long size = 0L;

      int read;
      for (byte[] buf = new byte[256]; (read = is.read(buf)) != -1; size += (long) read) {;
      }

      long var6 = size;
      return var6;
    } finally {
      try {
        is.close();
      } catch (IOException var14) {;
      }
    }
  }

  @Override
  public long lastModified() throws IOException {
    File fileToCheck = this.getFileForLastModifiedCheck();
    long lastModified = fileToCheck.lastModified();
    if (lastModified == 0L && !fileToCheck.exists()) {
      throw new FileNotFoundException(
          this.getDescription()
              + " cannot be resolved in the file system for checking its last-modified timestamp");
    } else {
      return lastModified;
    }
  }

  protected File getFileForLastModifiedCheck() throws IOException {
    return this.getFile();
  }

  @Override
  public Resource createRelative(String relativePath) throws IOException {
    throw new FileNotFoundException(
        "Cannot create a relative resource for " + this.getDescription());
  }

  @Override
  public String getFilename() {
    return null;
  }

  @Override
  public boolean equals(Object other) {
    return this == other
        || other instanceof Resource
            && ((Resource) other).getDescription().equals(this.getDescription());
  }

  @Override
  public int hashCode() {
    return this.getDescription().hashCode();
  }

  @Override
  public String toString() {
    return this.getDescription();
  }
}
