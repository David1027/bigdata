package com.shoestp.mains.repositorys.metaData;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoestp.mains.entitys.MetaData.GooglePageProperty;

public interface GooglePagePropertyInfoRepository extends JpaRepository<GooglePageProperty, Integer> {

  public Optional<GooglePageProperty> findTopByOrderByCreateTimeDesc();
}
