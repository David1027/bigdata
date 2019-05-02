package setBean.pojo;

public interface SetAfter<T, S> {

  void after(S s, T t);
}
