package com.shoestp.mains.repositorys.metaData;

import java.util.Optional;

import com.shoestp.mains.entitys.MetaData.GooglePageProperty;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GooglePagePropertyInfoRepository
    extends JpaRepository<GooglePageProperty, Integer> {

  Optional<GooglePageProperty> findTopByOrderByCreateTimeDesc();
}
