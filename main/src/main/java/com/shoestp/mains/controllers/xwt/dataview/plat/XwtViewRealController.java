package com.shoestp.mains.controllers.xwt.dataview.plat;

import java.util.Date;

import javax.annotation.Resource;

import com.shoestp.mains.controllers.xwt.dataview.plat.dto.real.XwtRealDTO;
import com.shoestp.mains.enums.flow.SourceTypeEnum;
import com.shoestp.mains.pojo.MessageResult;
import com.shoestp.mains.service.xwt.dataview.XwtViewRealService;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 实时-控制器
 * @author: lingjian
 * @create: 2020/1/3 10:00
 */
@RestController
@RequestMapping("/xwt/plat/real/")
public class XwtViewRealController {

  @Resource private XwtViewRealService service;
  @Resource private XwtRealDTO dto;

  /**
   * 根据时间，关键字获取实时数据分析时段分布
   *
   * @author: lingjian @Date: 2020/1/3 11:05
   * @param date 时间
   * @param indexCode 关键字
   * @return Map<String, Map>
   */
  @PostMapping(value = "index_trend")
  public Object getIndexTrend(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date, String indexCode) {
    return MessageResult.builder()
        .code(1)
        .msg("Hello")
        .result(service.getIndexTrend(date, indexCode))
        .build();
  }

  /**
   * 获取首页整体看板概况
   *
   * @author: lingjian @Date: 2020/1/3 11:03
   * @param date 时间
   * @param num 天数
   * @return XwtIndexOverVO 首页整体看板前端展示类对象
   */
  @GetMapping(value = "index_over_view")
  public Object getIndexOverview(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date, Integer num) {
    return MessageResult.builder()
        .code(1)
        .result(dto.toVo(service.getIndexOverview(date, num)))
        .build();
  }

  /**
   * 获取首页整体看板时段分布
   *
   * @author: lingjian @Date: 2020/1/3 13:43
   * @param date 时间
   * @param num 天数
   * @param indexCode 关键字
   * @return Map
   */
  @GetMapping(value = "index_over_view_time")
  public Object getIndexOverviewTime(
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date date, Integer num, String indexCode) {
    return MessageResult.builder()
        .code(1)
        .msg("Hello")
        .result(service.getIndexOverviewTime(date, num, indexCode))
        .build();
  }

  /**
   * 获取实时概况
   *
   * @author: lingjian @Date: 2019/5/9 15:45
   * @return XwtIndexOverVO 首页整体看板前端展示类对象
   */
  @GetMapping(value = "real_over_view")
  public Object getRealOverview() {
    return MessageResult.builder()
        .code(1)
        .msg("Hello")
        .result(dto.toVo(service.getRealOverview()))
        .build();
  }

  /**
   * 获取实时趋势
   *
   * @author: lingjian @Date: 2019/5/9 15:51
   * @param date 时间
   * @return Map<String, Map>
   */
  @PostMapping(value = "real_trend")
  public Object getRealTrend(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
    return MessageResult.builder().code(1).msg("Hello").result(service.getRealTrend(date)).build();
  }

  /**
   * 获取流量来源列表
   *
   * @author: lingjian @Date: 2020/1/3 13:49
   * @return Object对象
   */
  @GetMapping(value = "source_type")
  public Object getSourceType() {
    return MessageResult.builder().code(1).result(service.getSourceType()).build();
  }

  /**
   * 获取实时访客列表
   *
   * @author: lingjian @Date: 2019/8/20 13:53
   * @param page 开始条数
   * @param limit 显示条数
   * @param visitType 访客类型
   * @param sourceType 流量来源类型
   * @param urlPage 被访问页面
   * @param country 访客位置
   * @return Object对象
   */
  @GetMapping(value = "real_visitor")
  public Object getRealVisitor(
      Integer page,
      Integer limit,
      Integer visitType,
      SourceTypeEnum sourceType,
      String urlPage,
      Integer country) {
    return MessageResult.builder()
        .code(1)
        .result(service.getRealVisitor(page, limit, visitType, sourceType, urlPage, country))
        .build();
  }

  /**
   * 获取首页常访问页面列表
   *
   * @author: lingjian @Date: 2020/1/3 14:20
   * @param startDate 开始时间
   * @param endDate 结束时间
   * @param page 开始条目
   * @param limit 结束条目
   * @return Object对象
   */
  @GetMapping(value = "real_visit_page")
  public Object getRealVisitPage(
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
      Integer page,
      Integer limit) {
    return MessageResult.builder()
        .code(1)
        .msg("Hello")
        .result(service.getRealVisitPage(startDate, endDate, page, limit))
        .build();
  }

  /**
   * 获取页面分析页面访问排行列表
   *
   * @author: lingjian @Date: 2020/1/3 14:23
   * @param date 时间
   * @param num 天数类型
   * @param page 开始条数
   * @param limit 结束条数
   * @return Object对象
   */
  @GetMapping(value = "real_visit_page_num")
  public Object getRealVisitPage(
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date date, Integer num, Integer page, Integer limit) {
    return MessageResult.builder()
        .code(1)
        .msg("Hello")
        .result(service.getRealVisitPage(date, num, page, limit))
        .build();
  }
}
