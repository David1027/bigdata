package com.shoestp.mains.entitys;

import com.shoestp.mains.enums.UserRole;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
public class User {
  @Id @GeneratedValue private Integer id;
  private String user;
  private String password;

  @Enumerated(EnumType.STRING)
  private UserRole role;

  private String status;
  private String email;
}
