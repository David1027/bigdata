package com.shoestp.mains.entitys;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
public class User {
  @Id
  @GeneratedValue
  private Integer id;
  private String user;
  private String password;
  private String role;
  private String status;
  private String email;
}
