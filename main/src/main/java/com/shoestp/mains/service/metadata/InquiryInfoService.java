package com.shoestp.mains.service.metadata;

import com.shoestp.mains.entitys.metadata.InquiryInfo;
import com.shoestp.mains.enums.inquiry.InquiryTypeEnum;
import com.shoestp.mains.rpc.shoestp.pojo.GRPC_SendDataProto;
import com.shoestp.mains.views.Page;

import java.time.LocalDateTime;

/**
 * Created by IntelliJ IDEA. User: lijie@shoestp.cn Date: 2019/5/20 Time: 11:27 @author lijie
 *
 * @author lijie
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
  void syncInquiry(GRPC_SendDataProto.Inquiry info);

  /**
   * Count by pkey and type 根据产品/商家的 id 和类型统计某个时间段内的询盘总数
   *
   * @author lijie
   * @date 2019 /09/16
   * @since long.
   * @param pkey the pkey
   * @param type the type
   * @param start the start
   * @param end the end
   * @return the long
   */
  Long countByPkeyAndType(
      Integer pkey, InquiryTypeEnum type, LocalDateTime start, LocalDateTime end);

  /**
   * Gets inquiry.
   * 根据类型查询出相应的询盘
   * @author lijie
   * @date 2019 /09/19
   * @since
   */
  Page<InquiryInfo> getInquiry(InquiryTypeEnum type);
}
