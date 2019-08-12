package com.shoestp.mains.service.metadata.impl;

import com.shoestp.mains.dao.metadata.FavoriteDao;
import com.shoestp.mains.rpc.shoestp.pojo.GRPC_SendDataProto;
import com.shoestp.mains.service.metadata.FavoriteService;
import com.shoestp.mains.service.metadata.LocationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class FavoriteServiceImpl implements FavoriteService {
  @Resource private FavoriteDao favoriteDao;
  @Resource private LocationService locationService;

  @Override
  public void save(GRPC_SendDataProto.Favorite fa) {
    com.shoestp.mains.entitys.metadata.Favorite favorite =
        new com.shoestp.mains.entitys.metadata.Favorite();
    if (fa.getStatus() == 0) {
      String[] splitPkye = fa.getPkey().split(",");
      for (String str : splitPkye) {
        favoriteDao.removeByPkey(Integer.valueOf(str));
      }
      return;
    }
    favorite.setPkey(Integer.valueOf(fa.getPkey()));
    favorite.setImg(fa.getImg());
    favorite.setName(fa.getName());
    favorite.setPdtId(fa.getPdtId());
    favorite.setSupId(fa.getSupId());
    favorite.setCountry(locationService.getCountryByIp(fa.getIp()));
    favorite.setCreateTime(new Date());
    favoriteDao.save(favorite);
  }

  @Override
  public void syncUserInfo(GRPC_SendDataProto.Favorite info) {
    if (!favoriteDao.findByPkey(Integer.valueOf(info.getPkey())).isPresent()) {
      save(info);
    }
  }
}
