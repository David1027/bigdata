package org.start2do.utils.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.NoSuchFileException;
import java.nio.file.StandardOpenOption;
import org.start2do.utils.ResourceUtils;

/** Created by IntelliJ IDEA. User: Lijie HelloBox@outlook.com Date: 2019/5/31 Time: 13:48 */
public abstract class AbstractFileResolvingResource extends AbstractResource {
  @Override
  public boolean exists() {
    try {
      URL url = this.getURL();
      if (ResourceUtils.isFileURL(url)) {
        return this.getFile().exists();
      } else {
        URLConnection con = url.openConnection();
        this.customizeConnection(con);
        HttpURLConnection httpCon =
            con instanceof HttpURLConnection ? (HttpURLConnection) con : null;
        if (httpCon != null) {
          int code = httpCon.getResponseCode();
          if (code == 200) {
            return true;
          }

          if (code == 404) {
            return false;
          }
        }

        if (con.getContentLengthLong() > 0L) {
          return true;
        } else if (httpCon != null) {
          httpCon.disconnect();
          return false;
        } else {
          this.getInputStream().close();
          return true;
        }
      }
    } catch (IOException var5) {
      return false;
    }
  }

  @Override
  public boolean isReadable() {
    try {
      URL url = this.getURL();
      if (!ResourceUtils.isFileURL(url)) {
        URLConnection con = url.openConnection();
        this.customizeConnection(con);
        if (con instanceof HttpURLConnection) {
          HttpURLConnection httpCon = (HttpURLConnection) con;
          int code = httpCon.getResponseCode();
          if (code != 200) {
            httpCon.disconnect();
            return false;
          }
        }

        long contentLength = con.getContentLengthLong();
        if (contentLength > 0L) {
          return true;
        } else if (contentLength == 0L) {
          return false;
        } else {
          this.getInputStream().close();
          return true;
        }
      } else {
        File file = this.getFile();
        return file.canRead() && !file.isDirectory();
      }
    } catch (IOException var5) {
      return false;
    }
  }

  @Override
  public boolean isFile() {
    try {
      URL url = this.getURL();
      return "file".equals(url.getProtocol());
    } catch (IOException var2) {
      return false;
    }
  }

  @Override
  public File getFile() throws IOException {
    URL url = this.getURL();
    return ResourceUtils.getFile(url, this.getDescription());
  }

  @Override
  protected File getFileForLastModifiedCheck() throws IOException {
    URL url = this.getURL();
    if (ResourceUtils.isJarURL(url)) {
      URL actualUrl = ResourceUtils.extractArchiveURL(url);
      return ResourceUtils.getFile(actualUrl, "Jar URL");
    } else {
      return this.getFile();
    }
  }

  protected boolean isFile(URI uri) {
    return "file".equals(uri.getScheme());
  }

  protected File getFile(URI uri) throws IOException {
    return ResourceUtils.getFile(uri, this.getDescription());
  }

  @Override
  public ReadableByteChannel readableChannel() throws IOException {
    try {
      return FileChannel.open(this.getFile().toPath(), StandardOpenOption.READ);
    } catch (NoSuchFileException | FileNotFoundException var2) {
      return super.readableChannel();
    }
  }

  @Override
  public long contentLength() throws IOException {
    URL url = this.getURL();
    if (ResourceUtils.isFileURL(url)) {
      File file = this.getFile();
      long length = file.length();
      if (length == 0L && !file.exists()) {
        throw new FileNotFoundException(
            this.getDescription()
                + " cannot be resolved in the file system for checking its content length");
      } else {
        return length;
      }
    } else {
      URLConnection con = url.openConnection();
      this.customizeConnection(con);
      return con.getContentLengthLong();
    }
  }

  @Override
  public long lastModified() throws IOException {
    URL url = this.getURL();
    boolean fileCheck = false;
    long lastModified;
    if (ResourceUtils.isFileURL(url) || ResourceUtils.isJarURL(url)) {
      fileCheck = true;

      try {
        File fileToCheck = this.getFileForLastModifiedCheck();
        lastModified = fileToCheck.lastModified();
        if (lastModified > 0L || fileToCheck.exists()) {
          return lastModified;
        }
      } catch (FileNotFoundException var6) {;
      }
    }

    URLConnection con = url.openConnection();
    this.customizeConnection(con);
    lastModified = con.getLastModified();
    if (fileCheck && lastModified == 0L && con.getContentLengthLong() <= 0L) {
      throw new FileNotFoundException(
          this.getDescription()
              + " cannot be resolved in the file system for checking its last-modified timestamp");
    } else {
      return lastModified;
    }
  }

  protected void customizeConnection(URLConnection con) throws IOException {
    ResourceUtils.useCachesIfNecessary(con);
    if (con instanceof HttpURLConnection) {
      this.customizeConnection((HttpURLConnection) con);
    }
  }

  protected void customizeConnection(HttpURLConnection con) throws IOException {
    con.setRequestMethod("HEAD");
  }
}
