package com.shoestp.mains.dao.shoestpData;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoestp.mains.entitys.MetaData.WebVisitInfo;

public interface WebVisitInfoDao extends JpaRepository<WebVisitInfo, Integer> {

  // 查询首页的点击人数
  // 查询商品详情页的点击人数
  // 查询搜索页结果也的点击人数
  // 查询svs页的点击人数
  // 查询其他页的点击人数
  public int getClikeNum(Date startDate, Date endDate, String... str);

  public List<WebVisitInfo> findByCreateTimeBetween(Date startDate, Date endDate);

  public Map<String, Object> queryList(
      int type, int source, String page, String country, int start, int limit);
}
