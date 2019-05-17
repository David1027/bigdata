package com.shoestp.mains.dao.shoestpData;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoestp.mains.entitys.MetaData.WebVisitInfo;

public interface WebVisitInfoRepository extends JpaRepository<WebVisitInfo, Integer> {

  // 查询首页的点击人数
  // 查询商品详情页的点击人数
  // 查询搜索页结果也的点击人数
  // 查询svs页的点击人数
  // 查询其他页的点击人数
}
