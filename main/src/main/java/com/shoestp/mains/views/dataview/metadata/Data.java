package com.shoestp.mains.views.dataview.metadata;

@lombok.Data
public class Data {
  private String key;
  private Integer number;

  public Data(String key, Integer number) {
    this.key = key;
    this.number = number;
  }
}
