package org.start2do.mains.trigger.pojo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TriggerPojo {
    private String Key;
    private String Secret;
}
