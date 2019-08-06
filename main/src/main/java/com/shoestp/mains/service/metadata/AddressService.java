package com.shoestp.mains.service.metadata;

import com.shoestp.mains.entitys.metadata.PltCountry;
import com.shoestp.mains.entitys.metadata.Province;

public interface AddressService {
  PltCountry getCountry(String name);

  Province getProvince(String address);
}
