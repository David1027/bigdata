package com.shoestp.mains.dao.metadata;

import com.shoestp.mains.entitys.metadata.WebVisitInfo;
import com.shoestp.mains.enums.flow.AccessTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository(value = "com.shoestp.mains.dao.metadata.WebVisitDao")
public interface WebVisitInfoDao extends JpaRepository<WebVisitInfo, Integer> {

  Page<WebVisitInfo> findAllByPageTypeInAndCreateTimeGreaterThanAndCreateTimeLessThan(
      AccessTypeEnum[] accessTypeEnums, Date start, Date end,Pageable pageable);
}
