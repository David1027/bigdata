package com.shoestp.mains.repositorys.metadata;

import com.shoestp.mains.entitys.metadata.GooglePageProperty;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GooglePagePropertyInfoRepository
    extends JpaRepository<GooglePageProperty, Integer> {

  Optional<GooglePageProperty> findTopByOrderByCreateTimeDesc();
}
