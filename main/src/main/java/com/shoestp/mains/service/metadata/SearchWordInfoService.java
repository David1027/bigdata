package com.shoestp.mains.service.metadata;

import com.shoestp.mains.entitys.metadata.SearchWordInfo;
import com.shoestp.mains.views.dataview.utils.KeyValue;
import com.shoestp.mains.views.Page;
import java.util.Date;

/** Created by IntelliJ IDEA. User: lijie@shoestp.cn Date: 2019/5/20 Time: 11:22 */
public interface SearchWordInfoService {
  SearchWordInfo save(SearchWordInfo searchInfo);

  public Page<KeyValue> getRanking(Date endTime, Integer num, int start, int limit);
}
