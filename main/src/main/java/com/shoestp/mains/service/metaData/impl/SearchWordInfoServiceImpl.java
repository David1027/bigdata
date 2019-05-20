package com.shoestp.mains.service.metaData.impl;

import com.shoestp.mains.dao.shoestpData.SearchDao;
import com.shoestp.mains.entitys.MetaData.SearchWordInfo;
import com.shoestp.mains.service.metaData.SearchWordInfoService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/** Created by IntelliJ IDEA. User: lijie@shoestp.cn Date: 2019/5/20 Time: 11:24 */
@Service
public class SearchWordInfoServiceImpl implements SearchWordInfoService {

  @Resource private SearchDao searchDao;

  @Override
  public SearchWordInfo save(SearchWordInfo searchInfo) {
    return searchDao.save(searchInfo);
  }
}
