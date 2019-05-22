package com.shoestp.mains.views.DataView.metaData;

import java.util.Date;

import lombok.Data;

@Data
public class QueryWebVisitInfoView {

  private Integer id;
  private Date date;
  private String source;
  private String page;
  private String loation;
  private String name;
}
