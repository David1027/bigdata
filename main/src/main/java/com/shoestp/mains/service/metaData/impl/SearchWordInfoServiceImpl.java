package com.shoestp.mains.service.metaData.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shoestp.mains.dao.shoestpData.SearchDao;
import com.shoestp.mains.entitys.MetaData.SearchWordInfo;
import com.shoestp.mains.service.metaData.SearchWordInfoService;
import com.shoestp.mains.utils.dateUtils.DateTimeUtil;
import com.shoestp.mains.views.DataView.utils.KeyValue;

/** Created by IntelliJ IDEA. User: lijie@shoestp.cn Date: 2019/5/20 Time: 11:24 */
@Service
public class SearchWordInfoServiceImpl implements SearchWordInfoService {

  @Resource private SearchDao searchDao;

  @Override
  public SearchWordInfo save(SearchWordInfo searchInfo) {
    return searchDao.save(searchInfo);
  }

  @Override
  public List<KeyValue> getRanking(Date endTime, Integer num, int start, int limit) {
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
    List<Object> ranking = searchDao.getRanking(startTime, endTime, start, limit);
    List<KeyValue> kv = new ArrayList<>();
    for (Object item : ranking) {
      Object[] o = (Object[]) item;
      KeyValue k = new KeyValue();
      k.setKey(o[0].toString());
      k.setValue(o[1].toString());
      kv.add(k);
    }
    return kv;
  }
}
