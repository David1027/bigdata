package ipdb;

import org.junit.Test;
import org.start2do.utils.ResourceUtils;
import org.start2do.utils.iputils.IDC;

import java.io.FileInputStream;
import java.io.IOException;

public class IDCTest {

  @Test
  public void testIDC() {
    try {
      IDC db = new IDC(new FileInputStream(ResourceUtils.getFile("classpath:ip.ipdb")));
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
