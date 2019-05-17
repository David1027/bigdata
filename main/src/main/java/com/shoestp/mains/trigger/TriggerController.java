package com.shoestp.mains.trigger;

import javax.annotation.Resource;

import com.shoestp.mains.pojo.MessageResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trigger")
public class TriggerController {

  @Resource Test test;

  @GetMapping(value = {"/", ""})
  public Object trigger() {
    return MessageResult.builder().code(1).msg(String.valueOf(test.rand())).build();
  };
}
