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
 * @modify Lijie HelloBox@outlook.com 2019/8/27 9:57 上午 添加了规则的描述信息,用于区分具体规则的用途
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
  /** 规则优先级 */
  private Integer priority;
  /** 规则的描述信息 */
  private String description;
}
