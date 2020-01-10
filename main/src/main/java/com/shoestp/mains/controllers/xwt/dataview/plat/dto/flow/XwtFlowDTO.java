package com.shoestp.mains.controllers.xwt.dataview.plat.dto.flow;

import java.util.List;
import java.util.stream.Collectors;

import com.shoestp.mains.controllers.xwt.dataview.plat.vo.flow.XwtFlowDeviceVO;
import com.shoestp.mains.controllers.xwt.dataview.plat.vo.flow.XwtFlowPageVO;
import com.shoestp.mains.controllers.xwt.dataview.plat.vo.flow.XwtFlowSourceVO;
import com.shoestp.mains.entitys.xwt.dataview.plat.flow.XwtViewFlow;
import com.shoestp.mains.entitys.xwt.dataview.plat.flow.XwtViewFlowPage;
import com.shoestp.mains.utils.xwt.BeanCopyUtils;

import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * @description: 流量表DTO
 * @author: lingjian
 * @create: 2020/1/6 9:24
 */
@ToString
@Component
public class XwtFlowDTO {

  /**
   * 将BO转换为VO
   *
   * @param flow 流量对象
   * @return XwtFlowDeviceVO 设备来源VO
   */
  private XwtFlowDeviceVO toVoDevice(XwtViewFlow flow) {
    return BeanCopyUtils.copyProperties(flow, XwtFlowDeviceVO.class);
  }

  /**
   * 将BO集合对象转换为VO集合对象
   *
   * @param list 流量集合对象
   * @return List<XwtFlowDeviceVO> 设备来源VO集合对象
   */
  public List<XwtFlowDeviceVO> toVoDeviceList(List<XwtViewFlow> list) {
    return list.parallelStream().map(this::toVoDevice).collect(Collectors.toList());
  }

  /**
   * 将BO转换为VO
   *
   * @param flow 流量对象
   * @return XwtFlowSourceVO 来源类型VO
   */
  private XwtFlowSourceVO toVoSource(XwtViewFlow flow) {
    return BeanCopyUtils.copyProperties(flow, XwtFlowSourceVO.class);
  }

  /**
   * 将BO集合对象转换为VO集合对象
   *
   * @param list 流量集合对象
   * @return List<XwtFlowSourceVO> 来源类型VO集合对象
   */
  public List<XwtFlowSourceVO> toVoSourceList(List<XwtViewFlow> list) {
    return list.parallelStream().map(this::toVoSource).collect(Collectors.toList());
  }

  /**
   * 将BO转换为VO
   *
   * @param flowPage 流量页面对象
   * @return XwtFlowPageVO 页面VO对象
   */
  public XwtFlowPageVO toVo(XwtViewFlowPage flowPage) {
    return BeanCopyUtils.copyProperties(flowPage, XwtFlowPageVO.class);
  }
}
