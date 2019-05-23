package com.shoestp.mains.controllers.metaData;

import com.shoestp.mains.pojo.MessageResult;
import com.shoestp.mains.service.metaData.GoogleBrowseInfoSevice;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
  public Object getPageRanking(
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
      @RequestParam(defaultValue = "50") Integer limit) {
    return MessageResult.builder()
        .code(1)
        .msg("Hello")
        .result(browseInfo.getPageRanking(startTime, endTime, limit))
        .build();
  }
}
