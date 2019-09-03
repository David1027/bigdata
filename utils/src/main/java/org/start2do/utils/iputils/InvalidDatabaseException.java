package org.start2do.utils.iputils;

import java.io.IOException;

public class InvalidDatabaseException extends IOException {

  private static final long serialVersionUID = 7818375828106090155L;

  public InvalidDatabaseException(String message) {
    super(message);
  }
}
