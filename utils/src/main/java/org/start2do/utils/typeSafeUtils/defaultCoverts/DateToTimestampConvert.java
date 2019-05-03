package org.start2do.utils.typeSafeUtils.defaultCoverts;

import org.start2do.utils.typeSafe.TypeSafe;
import org.start2do.utils.typeSafe.TypeSafeConvert;

import java.sql.Timestamp;
import java.util.Date;

@TypeSafe
public class DateToTimestampConvert implements TypeSafeConvert<Date, Timestamp> {

  @Override
  public Timestamp convert(Date source) {
    return new Timestamp(source.getTime());
  }
}
