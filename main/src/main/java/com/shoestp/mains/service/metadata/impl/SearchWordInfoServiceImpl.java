package com.shoestp.mains.service.metadata.impl;

import com.shoestp.mains.dao.shoestpdata.SearchDao;
import com.shoestp.mains.entitys.metadata.SearchWordInfo;
import com.shoestp.mains.rpc.shoestp.pojo.GRPC_SendDataProto;
import com.shoestp.mains.service.metadata.LocationService;
import com.shoestp.mains.service.metadata.SearchWordInfoService;
import com.shoestp.mains.service.metadata.UserInfoService;
import com.shoestp.mains.utils.dateUtils.DateTimeUtil;
import com.shoestp.mains.views.Page;
import com.shoestp.mains.views.dataview.utils.KeyValue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/** Created by IntelliJ IDEA. User: lijie@shoestp.cn Date: 2019/5/20 Time: 11:24 */
@Service
public class SearchWordInfoServiceImpl implements SearchWordInfoService {
  private static final Logger logger = LogManager.getLogger(SearchWordInfoServiceImpl.class);

  @Resource private SearchDao searchDao;
  @Resource private UserInfoService userInfoService;

  @Resource private LocationService locationService;

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

  @Override
  public List<KeyValue> getRankingByCountry(String country) {
    List<Object> rankingByCountry = searchDao.getRankingByCountry(country);
    List<KeyValue> list = new ArrayList<>();
    for (Object item : rankingByCountry) {
      Object[] o = (Object[]) item;
      KeyValue k = new KeyValue();
      k.setKey(o[0].toString());
      k.setValue(o[1]);
      list.add(k);
    }
    return list;
  }

  @Override
  public void save(GRPC_SendDataProto.SearchInfo searchInfo) {
    logger.debug(searchInfo);
    SearchWordInfo searchWordInfo = new SearchWordInfo();
    searchWordInfo.setIp(searchInfo.getIp());
    searchWordInfo.setKeyword(searchInfo.getKeyword());
    searchWordInfo.setCountry(locationService.getCountryByIp(searchInfo.getIp()));
    searchWordInfo.setUserId(
        userInfoService.getUserInfo(searchInfo.getUserId(), searchInfo.getSign()));
    searchWordInfo.setCreateTime(new Date());
    searchDao.save(searchWordInfo);
  }
}
