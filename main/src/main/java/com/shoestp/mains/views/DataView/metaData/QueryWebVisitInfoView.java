package com.shoestp.mains.views.DataView.metaData;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class QueryWebVisitInfoView {

  private Integer id;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date date;

  private String source;
  private String page;
  private String loation;
  private String name;
}
