package com.shoestp.mains.trigger;

import javax.cache.annotation.CacheDefaults;
import javax.cache.annotation.CacheResult;

import org.springframework.stereotype.Service;

/** Created by IntelliJ IDEA. User: lijie@shoestp.cn Date: 2019/5/17 Time: 9:57 */
@Service
@CacheDefaults(cacheName = "default")
public class Test {
  @CacheResult
  public Double rand() {
    return Math.random();
  }
}
