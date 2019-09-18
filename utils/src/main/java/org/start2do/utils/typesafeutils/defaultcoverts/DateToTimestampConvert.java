package org.start2do.utils.typesafeutils.defaultcoverts;

import org.start2do.utils.typesafe.TypeSafe;
import org.start2do.utils.typesafe.TypeSafeConvert;

import java.sql.Timestamp;
import java.util.Date;

@TypeSafe
public class DateToTimestampConvert implements TypeSafeConvert<Date, Timestamp> {

  @Override
  public Timestamp convert(Date source) {
    return new Timestamp(source.getTime());
  }
}
