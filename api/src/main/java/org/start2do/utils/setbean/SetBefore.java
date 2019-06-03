package org.start2do.utils.setbean;

public interface SetBefore<S, T> {
  void before(S source, T target);
}
