package com.shoestp.mains.trigger;

import com.shoestp.mains.pojo.MessageResult;
import com.shoestp.mains.trigger.pojo.TriggerPojo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trigger")
public class TriggerController {

  @PostMapping("/")
  public Object trigger(TriggerPojo triggerPojo) {
    return MessageResult.builder().code(1).build();
  };

  /** */
  @PutMapping("/put/inquiry")
  public MessageResult saveInquiryInfo() {

    return MessageResult.builder().code(1).build();
  }
}
