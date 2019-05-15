package com.shoestp.mains.dao.shoestpData;

import com.shoestp.mains.entitys.MetaData.WebVisitInfo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewInfoDao extends JpaRepository<WebVisitInfo, Integer> {
}
