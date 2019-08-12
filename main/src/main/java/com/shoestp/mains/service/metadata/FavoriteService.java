package com.shoestp.mains.service.metadata;

import com.shoestp.mains.rpc.shoestp.pojo.GRPC_SendDataProto;

public interface FavoriteService {

    void save(GRPC_SendDataProto.Favorite fa);

    void syncUserInfo(GRPC_SendDataProto.Favorite info);
}
