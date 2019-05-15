package com.shoestp.mains.dao.shoestpData;

import com.shoestp.mains.entitys.MetaData.SearchWordInfo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchDao extends JpaRepository<SearchWordInfo,Integer> {
}
