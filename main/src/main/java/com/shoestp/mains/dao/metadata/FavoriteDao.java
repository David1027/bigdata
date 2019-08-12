package com.shoestp.mains.dao.metadata;

import com.shoestp.mains.entitys.metadata.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoriteDao extends JpaRepository<Favorite, Integer> {

  void removeByPkey(Integer pkey);

  Optional<Favorite> findByPkey(Integer pk);
}
