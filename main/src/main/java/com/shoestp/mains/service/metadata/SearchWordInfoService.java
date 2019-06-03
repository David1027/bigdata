package com.shoestp.mains.service.metadata;

import java.util.Date;
import java.util.List;

import com.shoestp.mains.entitys.metadata.SearchWordInfo;
import com.shoestp.mains.views.Page;
import com.shoestp.mains.views.dataview.utils.KeyValue;

/** Created by IntelliJ IDEA. User: lijie@shoestp.cn Date: 2019/5/20 Time: 11:22 */
public interface SearchWordInfoService {
  SearchWordInfo save(SearchWordInfo searchInfo);

   Page<KeyValue> getRanking(Date endTime, Integer num, int start, int limit);

   List<KeyValue> getRankingByCountry(String country);
}
