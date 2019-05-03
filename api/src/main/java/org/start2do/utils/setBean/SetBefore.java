package org.start2do.utils.setBean;

public interface SetBefore<S, T> {
  void before(S source, T target);
}
