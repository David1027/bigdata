package org.start2do.utils.typesafeutils.defaultcoverts;

import org.start2do.utils.typesafe.TypeSafe;
import org.start2do.utils.typesafe.TypeSafeConvert;

import java.sql.Timestamp;
import java.util.Date;

@TypeSafe
public class TimestampToDateConvert implements TypeSafeConvert<Timestamp, Date> {

  @Override
  public Date convert(Timestamp source) {
    return Date.from(source.toInstant());
  }
}
