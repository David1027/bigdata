package com.shoestp.mains.service.metaData;

import java.util.Date;
import java.util.List;

import com.shoestp.mains.entitys.MetaData.SearchWordInfo;
import com.shoestp.mains.views.DataView.utils.KeyValue;

/** Created by IntelliJ IDEA. User: lijie@shoestp.cn Date: 2019/5/20 Time: 11:22 */
public interface SearchWordInfoService {
  SearchWordInfo save(SearchWordInfo searchInfo);

  public List<KeyValue> getRanking(Date endTime, Integer num, int start, int limit);
}
