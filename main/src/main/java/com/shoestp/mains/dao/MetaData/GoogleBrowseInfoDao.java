package com.shoestp.mains.dao.MetaData;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoestp.mains.entitys.MetaData.GoogleBrowseInfo;

public interface GoogleBrowseInfoDao extends JpaRepository<GoogleBrowseInfo, Integer> {

  public Optional<GoogleBrowseInfo> findTopByOrderByCreateTimeDesc();
}
