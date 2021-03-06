package base;

import com.shoestp.mains.Runs;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@Rollback
@Transactional
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = Runs.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseTest {
  @Test
  public void say() {};
}
