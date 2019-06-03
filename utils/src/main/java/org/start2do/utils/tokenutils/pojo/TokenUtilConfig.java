package org.start2do.utils.tokenutils.pojo;

import lombok.Data;

@Data
public class TokenUtilConfig {
  private String Iv;
  private String Key;
  private String Md5Salt;
}
