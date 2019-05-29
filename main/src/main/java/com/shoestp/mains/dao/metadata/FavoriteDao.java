package com.shoestp.mains.dao.metadata;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shoestp.mains.entitys.metadata.Favorite;

public interface FavoriteDao extends JpaRepository<Favorite, Integer> {

  @Query(
      value =
          "SELECT country,img,name,pdt_id,sup_id,count(*) FROM `meta_data_favorite` WHERE create_time > ?1 AND create_time <= ?2 GROUP BY country,pdt_id",
      nativeQuery = true)
  List<Object> getPdtFavorite(Date startTime, Date endTime);
}
