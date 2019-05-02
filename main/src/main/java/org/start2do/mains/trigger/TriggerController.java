package org.start2do.mains.trigger;

import org.start2do.mains.trigger.pojo.TriggerPojo;
import org.start2do.mains.views.MessageResult;
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
