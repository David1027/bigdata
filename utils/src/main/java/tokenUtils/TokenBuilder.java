package tokenUtils;


import tokenUtils.pojo.TokenPojo;

/** Created by IntelliJ IDEA. User: Lijie HelloBox@outlook.com Date: 2019/4/24 Time: 10:16 */
public interface TokenBuilder<T> {
  TokenPojo build(T obj);
}
