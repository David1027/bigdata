package com.shoestp.mains.views;

import java.util.List;
import lombok.Data;

@Data
public class Page<T> {

  private long total;
  private List<T> list;
}
