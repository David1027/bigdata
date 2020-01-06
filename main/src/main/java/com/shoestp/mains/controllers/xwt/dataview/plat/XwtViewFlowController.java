package com.shoestp.mains.controllers.xwt.dataview.plat;

import java.util.Date;

import javax.annotation.Resource;

import com.shoestp.mains.controllers.xwt.dataview.plat.dto.flow.XwtFlowDTO;
import com.shoestp.mains.enums.xwt.OAccessTypeEnum;
import com.shoestp.mains.pojo.MessageResult;
import com.shoestp.mains.service.xwt.dataview.XwtViewFlowService;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 流量-控制器
 * @author: lingjian @Date: 2019/5/9 10:08
 */
@RestController
@RequestMapping("/xwt/plat/flow/")
public class XwtViewFlowController {

  @Resource private XwtViewFlowService service;
  @Resource private XwtFlowDTO dto;

  /**
   * 获取实时来源
   *
   * @author: lingjian @Date: 2020/1/6 9:06
   * @return Map<String, List>
   */
  @PostMapping(value = "real_source")
  public Object getRealSource(
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
    return MessageResult.builder()
        .code(1)
        .result(service.getRealSource(startDate, endDate))
        .build();
  }

  /**
   * 根据两个时间获取设备来源（重载）
   *
   * @author: lingjian @Date: 2020/1/6 9:18
   * @param startDate 开始时间
   * @param endDate 结束时间
   * @return List<XwtFlowDeviceVO> 设备来源VO集合对象
   */
  @PostMapping(value = "flow_device")
  public Object getFlowDevice(
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
    return MessageResult.builder()
        .code(1)
        .result(dto.toVoDeviceList(service.getFlowDevice(startDate, endDate)))
        .build();
  }

  /**
   * 根据一个时间，一个天数类型获取设备来源（重载）
   *
   * @author: lingjian @Date: 2020/1/6 9:28
   * @param date 时间
   * @param num 天数
   * @return List<XwtFlowDeviceVO> 设备来源VO集合对象
   */
  @PostMapping(value = "flow_device_by_num")
  public Object getFlowDevice(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date, Integer num) {
    return MessageResult.builder()
        .code(1)
        .result(dto.toVoDeviceList(service.getFlowDevice(date, num)))
        .build();
  }

  /**
   * 根据时间获取流量来源
   *
   * @author: lingjian @Date: 2020/1/6 9:30
   * @param date 时间
   * @param num 天数
   * @return List<XwtFlowSourceVO> 来源类型VO集合对象
   */
  @PostMapping(value = "flow_source_type")
  public Object getFlowSourceType(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date, Integer num) {
    return MessageResult.builder()
        .code(1)
        .result(dto.toVoSourceList(service.getFlowSourceType(date, num)))
        .build();
  }

  /**
   * 根据时间获取流量概况(小时)
   *
   * @author: lingjian @Date: 2020/1/6 9:43
   * @param date 时间
   * @return Map<String, Map>
   */
  @PostMapping(value = "flow_source_type_time_by_hour")
  public Object getFlowSourceTypeTimeByHour(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
    return MessageResult.builder()
        .code(1)
        .result(service.getFlowSourceTypeTimeByHour(date))
        .build();
  }

  /**
   * 根据时间获取流量概况(天)
   *
   * @author: lingjian @Date: 2020/1/6 9:58
   * @param date 时间
   * @param num 天数
   * @return Map<String, Map>
   */
  @PostMapping(value = "flow_source_type_time_by_day")
  public Object getFlowSourceTypeTimeByDay(
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date date, int num) {
    return MessageResult.builder()
        .code(1)
        .result(service.getFlowSourceTypeTimeByDay(num, date))
        .build();
  }

  /**
   * 获取所有的页面类型
   *
   * @author: lingjian @Date: 2020/1/6 10:16
   * @return List<OAccessTypeEnum> 页面类型集合
   */
  @GetMapping(value = "flow_page_type")
  public Object getFlowPageType() {
    return MessageResult.builder().code(1).result(service.getFlowPageType()).build();
  }

  /**
   * 根据时间获取页面分析
   *
   * @author: lingjian @Date: 2020/1/6 10:17
   * @param date 时间
   * @param num 天数类型
   * @return Map<String, List<XwtViewFlowPage>>
   */
  @PostMapping(value = "flow_page_analysis")
  public Object getFlowPageAnalysis(
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date date, Integer num) {
    return MessageResult.builder().code(1).result(service.getFlowPageAnalysis(date, num)).build();
  }

  /**
   * 根据时间和页面分类，获取页面分析时段分析(小时)
   *
   * @author: lingjian @Date: 2020/1/6 10:33
   * @param date 时间
   * @param access 页面来源类型
   * @return Map<String, Map>
   */
  @PostMapping(value = "flow_page_analysis_by_hour")
  public Object getFlowPageAnalysisByHour(
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date date, OAccessTypeEnum access) {
    return MessageResult.builder()
        .code(1)
        .result(service.getFlowPageAnalysisByHour(date, access))
        .build();
  }

  /**
   * 根据时间和页面分类，获取页面分析时段分析(天)
   *
   * @author: lingjian @Date: 2020/1/6 10:55
   * @param date 时间
   * @param access 页面来源类型
   * @param num 天数
   * @return Map<String, Map>
   */
  @PostMapping(value = "flow_page_analysis_by_day")
  public Object getFlowPageAnalysisByDay(
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date date, OAccessTypeEnum access, Integer num) {
    return MessageResult.builder()
        .code(1)
        .result(service.getFlowPageAnalysisByDay(num, date, access))
        .build();
  }

  /**
   * 根据时间获取流量概况参数（跳失率，平均浏览量，平均停留时长）
   *
   * @author: lingjian @Date: 2020/1/6 11:00
   * @param date 时间
   * @param num 天数类型
   * @return XwtFlowPageVO 页面VO
   */
  @PostMapping(value = "flow_page")
  public Object getFlowPage(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date, Integer num) {
    return MessageResult.builder().code(1).result(dto.toVo(service.getFlowPage(date, num))).build();
  }

  /**
   * 根据时间获取一个三十天中每一天的流量概况参数
   *
   * @author: lingjian @Date: 2020/1/6 11:17
   * @param date 时间
   * @return
   */
  @PostMapping(value = "flow_page_by_month")
  public Object getFlowPageByMonth(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
    return MessageResult.builder().code(1).result(service.getFlowPageByMonth(date)).build();
  }
}
