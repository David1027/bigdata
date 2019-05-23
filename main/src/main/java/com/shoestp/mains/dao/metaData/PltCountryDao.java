package com.shoestp.mains.dao.metaData;

import com.shoestp.mains.entitys.metaData.PltCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "pltCountryDao")
public interface PltCountryDao extends JpaRepository<PltCountry, Integer> {}
