package com.google.musicstore.client.dto;

import java.io.Serializable;
import java.util.Set;

public class AccountDTO implements Serializable {
  private Long id;
  private String name;
  private String password;
  private Set<RecordDTO> records;

  public AccountDTO() {
  }

  public AccountDTO(Long id) {
    this.id = id;
  }

  public AccountDTO(Long id, String name, String password,
      Set<RecordDTO> records) {
    this.id = id;
    this.name = name;
    this.password = password;
    this.records = records;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<RecordDTO> getRecords() {
    return records;
  }

  public void setRecords(Set<RecordDTO> records) {
    this.records = records;
  }

}
