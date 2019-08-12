package com.shoestp.mains.service.metadata;

import com.shoestp.mains.entitys.metadata.PltCountry;
import com.shoestp.mains.entitys.metadata.Province;

import java.util.List;

/**
 * The interface Location service. 地址服务用于获取国家,地区信息
 *
 * @author lijie
 * @date 2019 /08/09
 * @since
 */
public interface LocationService {
  /**
   * Gets country list.
   *
   * @author lijie
   * @date 2019 /08/09
   * @since *
   * @return the country list
   */
  List<PltCountry> getCountryList();

  /**
   * Gets country by ip.
   *
   * @author lijie
   * @date 2019 /08/09
   * @since *
   * @param ip the ip
   * @return the country by ip
   */
  PltCountry getCountryByIp(String ip);

  /**
   * Gets country.
   *
   * @author lijie
   * @date 2019 /08/09
   * @since *
   * @param name the name
   * @return the country
   */
  PltCountry getCountry(String name);

  /**
   * Gets province.
   *
   * @author lijie
   * @date 2019 /08/09
   * @since *
   * @param address the address
   * @return the province
   */
  Province getProvince(String address);

  /**
   * Get address 根据Ip获取国家地区
   *
   * @author lijie
   * @date 2019 /08/09
   * @since string [ ].
   * @param ip the ip
   * @return the string [ ]
   */
  String[] getAddress(String ip);
}
