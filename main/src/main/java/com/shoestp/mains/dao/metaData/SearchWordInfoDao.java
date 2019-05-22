package com.shoestp.mains.dao.metaData;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoestp.mains.entitys.MetaData.SearchWordInfo;

public interface SearchWordInfoDao extends JpaRepository<SearchWordInfo, Integer> {

  public List<Object> getRanking();
}
