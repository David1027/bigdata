package com.shoestp.mains.dao.MetaData;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoestp.mains.entitys.MetaData.GooglePageProperty;

public interface GooglePagePropertyInfoDao extends JpaRepository<GooglePageProperty, Integer> {

  public Optional<GooglePageProperty> findTopByOrderByCreateTimeDesc();
}
