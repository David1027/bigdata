package com.shoestp.mains.controllers.metaData;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoestp.mains.pojo.MessageResult;
import com.shoestp.mains.service.metaData.SearchWordInfoService;

@RestController
@RequestMapping("/api/platform")
public class SearchWordInfoController {

  @Autowired private SearchWordInfoService searchWordInfoService;

  @GetMapping(value = "/geKeyRanking")
  public Object getKeyRankng(
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
      @RequestParam(defaultValue = "0") Integer num,
      @RequestParam(defaultValue = "0") int start,
      @RequestParam(defaultValue = "10") int limit) {
    return MessageResult.builder()
        .code(1)
        .msg("Hello")
        .result(searchWordInfoService.getRanking(endTime, num, start, limit))
        .build();
  }
}
