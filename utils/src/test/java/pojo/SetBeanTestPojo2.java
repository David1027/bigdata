package pojo;

import lombok.Data;
import org.start2do.utils.setbean.SetBean;

import java.sql.Timestamp;

@SetBean
@Data
public class SetBeanTestPojo2 {
  @SetBean(alias = "id")
  private int pkey;

  @SetBean(alias = "xxx")
  private String h;

  @SetBean(alias = "date")
  private Timestamp timestamp;
}
