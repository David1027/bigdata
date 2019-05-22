package com.shoestp.mains.config.shiro.pojo;

import java.util.List;
import lombok.Data;

@Data
public class UserInfo {
  private Integer id;
  private String name;
  private String email;
  private String role;
  private List<String> url;
}
