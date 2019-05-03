package org.start2do.utils.typeSafeUtils.defaultCoverts;

import org.start2do.utils.typeSafe.TypeSafe;
import org.start2do.utils.typeSafe.TypeSafeConvert;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@TypeSafe
public class DateToLocalDateConvert implements TypeSafeConvert<Date, LocalDate> {

  @Override
  public LocalDate convert(Date source) {
    return source.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
  }
}
