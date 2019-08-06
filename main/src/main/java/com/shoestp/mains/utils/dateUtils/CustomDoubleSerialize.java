package com.shoestp.mains.utils.dateUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @description: double保留2位
 * @author: lingjian
 * @create: 2019/5/20 10:59
 */
public class CustomDoubleSerialize extends JsonSerializer<Double> {

  private DecimalFormat df = new DecimalFormat("0.00");

  @Override
  public void serialize(Double value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException, JsonProcessingException {
    if (value != null) {
      gen.writeString(df.format(value));
    }
  }

  public static Double setDouble(Double d) {
    double df = 0.00;
    if (d != null) {
      BigDecimal b = BigDecimal.valueOf(d);
      df = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    return df;
  }

  public static void main(String[] args) {

  }
}
