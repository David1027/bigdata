package org.start2do.utils.typeSafeUtils.defaultCoverts;

import org.start2do.utils.typeSafe.TypeSafe;
import org.start2do.utils.typeSafe.TypeSafeConvert;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@TypeSafe
public class DateToLocalDateTimeConvert implements TypeSafeConvert<Date, LocalDateTime> {

  @Override
  public LocalDateTime convert(Date source) {
    return source.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
  }
}
