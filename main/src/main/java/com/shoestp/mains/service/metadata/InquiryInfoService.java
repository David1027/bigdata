package com.shoestp.mains.service.metadata;

import com.shoestp.mains.entitys.metadata.InquiryInfo;
import com.shoestp.mains.rpc.shoestp.pojo.GRPC_SendDataProto;

/**
 * Created by IntelliJ IDEA. User: lijie@shoestp.cn Date: 2019/5/20 Time: 11:27 @author lijie
 *
 * @date 2019 /08/09
 * @since
 */
public interface InquiryInfoService {
  /**
   * Save 保存询盘信息
   *
   * @author lijie
   * @date 2019 /08/09
   * @since inquiry info.
   * @param inquiryInfo the inquiry info
   * @return the inquiry info
   */
  InquiryInfo save(InquiryInfo inquiryInfo);

  /**
   * Save 保存询盘信息
   *
   * @author lijie
   * @date 2019 /08/09
   * @since .
   * @param inquiry the inquiry
   */
  void save(GRPC_SendDataProto.Inquiry inquiry);

  /**
   * Sync user info 同步询盘信息
   *
   * @author lijie
   * @date 2019 /08/09
   * @since .
   * @param info the info
   */
  void syncUserInfo(GRPC_SendDataProto.Inquiry info);
}
