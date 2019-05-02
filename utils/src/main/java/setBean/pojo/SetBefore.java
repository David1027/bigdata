package setBean.pojo;

public interface SetBefore<S, T> {
  void before(S source, T target);
}
