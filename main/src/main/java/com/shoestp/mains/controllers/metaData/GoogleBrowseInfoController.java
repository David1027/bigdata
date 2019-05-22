package com.shoestp.mains.controllers.metaData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoestp.mains.pojo.MessageResult;
import com.shoestp.mains.service.metaData.GoogleBrowseInfoSevice;

@RestController
@RequestMapping("/api/platform")
public class GoogleBrowseInfoController {

  @Autowired private GoogleBrowseInfoSevice browseInfo;

  /**
   * -获取页面分析接口 默认查询50条
   *
   * @param limit
   * @return
   */
  @GetMapping(value = "/getPageRanking")
  public Object getPageRanking(@RequestParam(defaultValue = "50") Integer limit) {
    return MessageResult.builder()
        .code(1)
        .msg("Hello")
        .result(browseInfo.getPageRanking(limit))
        .build();
  }
}
