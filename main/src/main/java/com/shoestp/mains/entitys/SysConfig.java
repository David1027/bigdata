package com.shoestp.mains.entitys;

import com.shoestp.mains.enums.SysconfigEnum;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Data;

/** Created by IntelliJ IDEA. User: lijie@shoestp.cn Date: 2019/5/8 Time: 14:07 */
@Entity
@Data
@Builder
public class SysConfig {
  @Id @GeneratedValue private Integer id;
  /** * 类型 */
  @Enumerated(EnumType.STRING)
  private SysconfigEnum type;
  /** * 键 */
  private String key;
  /** * 值 */
  private String value;
  /** * 备注 */
  private String remark;
}
