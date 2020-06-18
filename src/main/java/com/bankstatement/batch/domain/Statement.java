package com.bankstatement.batch.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Statement {
  private final Customer customer;
  private List<Account> accounts = new ArrayList<>();

  public Statement(Customer customer) {
    this.customer = customer;
  }
}
