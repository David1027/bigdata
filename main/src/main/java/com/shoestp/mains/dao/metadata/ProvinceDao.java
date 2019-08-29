package com.shoestp.mains.dao.metadata;

import com.shoestp.mains.entitys.metadata.Province;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProvinceDao extends JpaRepository<Province, Integer> {

  Optional<Province> findByNameLike(String name);
}
