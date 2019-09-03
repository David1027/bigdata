package ipdb;

import org.junit.Test;
import org.start2do.utils.ResourceUtils;
import org.start2do.utils.iputils.BaseStation;

import java.io.FileInputStream;
import java.io.IOException;

public class BaseStationTest {
  @Test
  public void testBaseStation() {
    try {
      BaseStation db = new BaseStation(new FileInputStream(ResourceUtils.getFile("classpath:ip.ipdb")));
      System.out.println(db.buildTime());
      System.out.println(db.languages());
      System.out.println(db.fields());
      System.out.println(db.isIPv4());
      System.out.println(db.isIPv6());
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }
}
