package com.shoestp.mains.service.metaData.impl;

import com.shoestp.mains.dao.shoestpData.SearchDao;
import com.shoestp.mains.entitys.metaData.SearchWordInfo;
import com.shoestp.mains.service.metaData.SearchWordInfoService;
import com.shoestp.mains.utils.dateUtils.DateTimeUtil;
import com.shoestp.mains.views.DataView.utils.KeyValue;
import com.shoestp.mains.views.Page;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
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

  @Override
  public Page<KeyValue> getRanking(Date endTime, Integer num, int start, int limit) {
    if (endTime == null) {
      endTime = new Date();
    }
    Date startTime = null;
    if (num.equals(7) || num.equals(30)) {
      num -= num * 2;
      startTime = DateTimeUtil.countDate(endTime, Calendar.DAY_OF_YEAR, num);
    } else {
      startTime = DateTimeUtil.getTimesOfDay(endTime);
      endTime = DateTimeUtil.getTimesnight(endTime);
    }
    Map<String, Object> map = searchDao.getRanking(startTime, endTime, start, limit);
    long count = (long) map.get("count");
    List<KeyValue> kv = (List<KeyValue>) map.get("list");
    Page<KeyValue> page = new Page<>();
    page.setList(kv);
    page.setTotal(count);
    return page;
  }
}
