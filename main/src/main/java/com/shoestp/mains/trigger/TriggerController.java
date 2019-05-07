package com.shoestp.mains.trigger;

import com.shoestp.mains.pojo.MessageResult;
import com.shoestp.mains.trigger.pojo.TriggerPojo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/trigger")
public class TriggerController {

  @PostMapping("/")
  public Object trigger(TriggerPojo triggerPojo) {
    return MessageResult.builder().code(1).build();
  };
}
