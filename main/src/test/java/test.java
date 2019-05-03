import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.junit.Test;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class test {

  @Test
  public void test1() {
    Cache<String, String> cache =
        Caffeine.newBuilder()
            .removalListener(
                (o, o2, removalCause) -> {
                  System.out.println(removalCause.wasEvicted());
                  System.out.println(o2);
                  System.out.println(o);
                })
            .expireAfterWrite(5, TimeUnit.SECONDS)
            .build();
    cache.put("hello", "123");
    System.out.println("存入完成");
    Scanner scanner = new Scanner(System.in);
    System.out.println(scanner.nextLine());
  }
}
