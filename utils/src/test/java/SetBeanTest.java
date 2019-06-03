import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.start2do.utils.setbean.SetBeanUtils;
import pojo.SetBeanTestPojo;
import pojo.SetBeanTestPojo2;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SetBeanTest {

  @Test
  public void mapSet() {
    Map<String, Object> map = new HashMap<>();
    map.put("id", 1);
    map.put("ppp", "test");
    SetBeanTestPojo pojo = SetBeanUtils.set(map, SetBeanTestPojo.class);
    System.out.println(pojo);
    Assert.assertEquals(pojo.getId(), map.get("id"));
    Assert.assertEquals(pojo.getXxx(), map.get("ppp"));
  }

  @Test
  public void ObjectSetTest1() {
    SetBeanTestPojo setBeanTestPojo = new SetBeanTestPojo();
    setBeanTestPojo.setId(1);
    setBeanTestPojo.setXxx("nihao");
    setBeanTestPojo.setDate(new Date());
    SetBeanTestPojo2 setBeanTestPojo2 = SetBeanUtils.set(setBeanTestPojo, SetBeanTestPojo2.class);
    System.out.println(setBeanTestPojo2);
    Assert.assertEquals(setBeanTestPojo.getId(), Integer.valueOf(setBeanTestPojo2.getPkey()));
    Assert.assertEquals(setBeanTestPojo.getXxx(), setBeanTestPojo2.getH());
  }

  @Test
  public void ObjectSetTest2() {
    SetBeanTestPojo2 setBeanTestPojo = new SetBeanTestPojo2();
    setBeanTestPojo.setPkey(1);
    setBeanTestPojo.setH("SetBeanTestPojo2是我");
    setBeanTestPojo.setTimestamp(new Timestamp(new Date().getTime()));
    SetBeanTestPojo pojo = SetBeanUtils.set(setBeanTestPojo, SetBeanTestPojo.class);
    System.out.println(pojo);
    Assert.assertEquals(pojo.getId(), Integer.valueOf(setBeanTestPojo.getPkey()));
    Assert.assertEquals(pojo.getXxx(), setBeanTestPojo.getH());
  }
}
