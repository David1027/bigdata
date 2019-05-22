package com.shoestp.mains.trigger;

import com.shoestp.mains.config.shiro.utils.JWTUtil;
import com.shoestp.mains.pojo.MessageResult;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trigger")
public class TriggerController {

  @Resource Test test;

  @GetMapping(value = {"/", ""})
  public MessageResult trigger() {
    if (JWTUtil.getUserInfo() == null) {
      return MessageResult.builder().code(2).build();
    }
    return MessageResult.builder().code(1).msg(String.valueOf(test.rand())).build();
  }

  @PostMapping(value = {"/", ""})
  public Object trigger1() {
    return MessageResult.builder().code(1).msg("123").build();
  }
}
