package com.shoestp.mains.dao.MetaData;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoestp.mains.entitys.MetaData.GoogleVisitorInfo;

public interface GoogleVisitorInfoDao extends JpaRepository<GoogleVisitorInfo, Integer> {

  public Optional<GoogleVisitorInfo> findTopByOrderByCreateTimeDesc();
}
