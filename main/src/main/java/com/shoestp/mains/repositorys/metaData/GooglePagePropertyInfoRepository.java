package com.shoestp.mains.repositorys.metaData;

import com.shoestp.mains.entitys.metaData.GooglePageProperty;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GooglePagePropertyInfoRepository
    extends JpaRepository<GooglePageProperty, Integer> {

  Optional<GooglePageProperty> findTopByOrderByCreateTimeDesc();
}
