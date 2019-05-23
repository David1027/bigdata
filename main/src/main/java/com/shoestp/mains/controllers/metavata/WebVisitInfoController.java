package com.shoestp.mains.controllers.metavata;

import com.shoestp.mains.pojo.MessageResult;
import com.shoestp.mains.service.metadata.WebVisitInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/platform")
public class WebVisitInfoController {

  @Autowired private WebVisitInfoService webVisitInfo;

  /**
   * -获取实时访客接口
   *
   * @param visitType 访客类型 数字1：注册会员
   * @param sourceType 流量来源 数字1：google 2：百度 3：自主访问 4：社交访问 0：全部
   * @param page 访问页面 空字符表示不限
   * @param country 国家名称字符
   * @param start 起始条数
   * @param limit 返回条数
   * @return
   */
  @GetMapping(value = "/getRealVisitInfo")
  public Object getVisitInfo(
      @RequestParam(defaultValue = "1") int visitType,
      @RequestParam(defaultValue = "0") int sourceType,
      String page,
      String country,
      @RequestParam(defaultValue = "0") int start,
      @RequestParam(defaultValue = "20") int limit) {
    return MessageResult.builder()
        .code(1)
        .msg("Hello")
        .result(webVisitInfo.getWebVisitInfo(visitType, sourceType, page, country, start, limit))
        .build();
  }
}
