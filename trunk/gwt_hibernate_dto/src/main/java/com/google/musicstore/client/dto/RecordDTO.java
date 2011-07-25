package com.google.musicstore.client.dto;

import java.io.Serializable;

public class RecordDTO implements Serializable {
  private Long id;
  private String title;
  private int year;
  private double price;

  public RecordDTO() {
  }

  public RecordDTO(Long id) {
    this.id = id;
  }

  public RecordDTO(Long id, String title, int year, double price) {
    this.id = id;
    this.title = title;
    this.year = year;
    this.price = price;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }
}
