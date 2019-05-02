package org.start2do.mains.entitys;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
