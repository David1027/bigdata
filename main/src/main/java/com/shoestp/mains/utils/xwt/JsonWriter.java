package com.shoestp.mains.utils.xwt;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/** @author liyichao */
public class JsonWriter {

  public static final String DEFAULT_CONTENT_TYPE = "application/json;charset=utf-8";

  private final ObjectMapper objectMapper =
      new ObjectMapper().setSerializationInclusion(Include.NON_NULL).setLocale(null);

  public ObjectMapper getObjectMapper() {
    return objectMapper;
  }

  public ObjectMapper getPrettyObjectMapper() {
    return pretty;
  }

  public void setLocale(Locale locale) {
    this.objectMapper.setLocale(locale);
  }

  public static final ObjectMapper oMapper =
      new ObjectMapper().setSerializationInclusion(Include.NON_NULL);

  private static final ObjectMapper pretty =
      new ObjectMapper()
          .setSerializationInclusion(Include.NON_NULL)
          .enable(SerializationFeature.INDENT_OUTPUT);

  public static ObjectMapper defaultMapper() {
    return pretty;
  }

  public static void toConsole(Object o) {
    try {
      System.out.println(pretty.writeValueAsString(o));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }

  public static String asString(Object o) throws JsonProcessingException {
    return pretty.writeValueAsString(o);
  }

  public static String asString4Log(String title, Object o) throws JsonProcessingException {
    return title + "]\r\n" + asString(o) + "\r\n";
  }

  public static <T> Map objectToMap(T obj) {
    Map map = null;
    try {
      map = JsonWriter.defaultMapper().readValue(JsonWriter.asString(obj), Map.class);
    } catch (IOException e) {
      return null;
    }
    return map;
  }
}
