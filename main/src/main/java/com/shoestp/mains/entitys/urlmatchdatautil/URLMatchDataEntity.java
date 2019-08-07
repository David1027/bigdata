package com.shoestp.mains.entitys.urlmatchdatautil;

import com.shoestp.mains.entitys.urlmatchdatautil.enums.URLDataTypeEnum;
import lombok.Data;

import javax.persistence.*;

/**
 * The type Url data. url匹配数据实体类
 *
 * @author lijie
 * @date 2019 /08/07
 * @since
 */
@Data
@Entity
public class URLMatchDataEntity {
  /** The Id. 主键 自增 */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /** The Result. 名称 */
  private String name;

  /** The Regex. 匹配表达式 */
  private String regex;
  /** 该规则的类型 */
  @Enumerated(EnumType.STRING)
  private URLDataTypeEnum type;
}
