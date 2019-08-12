package com.shoestp.mains.dao.metadata;

import com.shoestp.mains.entitys.metadata.WebVisitInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "com.shoestp.mains.dao.metadata.WebVisitDao")
public interface WebVisitInfoDao extends JpaRepository<WebVisitInfo, Integer> {}
