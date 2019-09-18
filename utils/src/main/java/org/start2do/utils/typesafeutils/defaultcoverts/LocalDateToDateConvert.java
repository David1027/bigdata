package org.start2do.utils.typesafeutils.defaultcoverts;

import org.start2do.utils.typesafe.TypeSafe;
import org.start2do.utils.typesafe.TypeSafeConvert;

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
