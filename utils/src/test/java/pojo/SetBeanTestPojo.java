package pojo;

import lombok.Data;
import org.start2do.utils.setBean.SetBean;

import java.util.Date;

@Data
public class SetBeanTestPojo {
  private Integer id;

  @SetBean(alias = "ppp")
  private String xxx;

  private Date date;
}
