package com.shoestp.mains.entitys.MetaData;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Data;

/**
 * @description: 国家表
 * @author: lingjian
 * @create: 2019/5/8 10:04
 */
@Data
@Entity
@Builder
public class DataViewCountry {
    @Id
    @GeneratedValue
    private Integer id;
    /** 国家名称 */
    private String countryName;
    /** 国旗图片 */
    private String countryImage;
    /** 访客数 */
    private Integer visitorCount;
    /** 浏览量 */
    private Integer viewCount;
}
