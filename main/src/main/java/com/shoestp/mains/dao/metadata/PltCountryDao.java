package com.shoestp.mains.dao.metadata;

import com.shoestp.mains.entitys.metadata.PltCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "pltCountryDao")
public interface PltCountryDao extends JpaRepository<PltCountry, Integer> {}
