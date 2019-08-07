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
@ActiveProfiles("dev")
@SpringBootTest(classes = Runs.class)
public class BaseTest {
  @Test
  public void say() {};
}
