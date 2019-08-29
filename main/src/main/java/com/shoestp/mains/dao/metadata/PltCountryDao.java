package com.shoestp.mains.dao.metadata;

import com.shoestp.mains.entitys.metadata.PltCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository(value = "pltCountryDao")
public interface PltCountryDao extends JpaRepository<PltCountry, Integer> {

  Optional<PltCountry> findByNameLike(String name);

  Optional<PltCountry> findFirstByShortName(String name);
}
