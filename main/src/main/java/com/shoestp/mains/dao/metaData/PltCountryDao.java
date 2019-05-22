package com.shoestp.mains.dao.metaData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shoestp.mains.entitys.MetaData.PltCountry;

@Repository(value = "pltCountryDao")
public interface PltCountryDao extends JpaRepository<PltCountry, Integer> {}
