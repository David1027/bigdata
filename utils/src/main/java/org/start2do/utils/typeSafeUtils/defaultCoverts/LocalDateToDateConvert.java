package org.start2do.utils.typeSafeUtils.defaultCoverts;

import org.start2do.utils.typeSafe.TypeSafe;
import org.start2do.utils.typeSafe.TypeSafeConvert;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@TypeSafe
public class LocalDateToDateConvert implements TypeSafeConvert<LocalDate, Date> {

  @Override
  public Date convert(LocalDate source) {
    return Date.from(source.atStartOfDay(ZoneId.systemDefault()).toInstant());
  }
}
