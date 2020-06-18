package com.bankstatement.batch.domain;

import lombok.*;
import org.springframework.util.StringUtils;


@Getter
@Setter
@ToString
public class CustomerNameUpdate extends CustomerUpdate {
  private final String firstName;
  private final String middleName;
  private final String lastName;

  public CustomerNameUpdate(long customerId, String firstName, String middleName, String lastName) {
    super(customerId);
    this.firstName = StringUtils.hasText(firstName) ? firstName : null;
    this.middleName = StringUtils.hasText(middleName) ? middleName : null;
    this.lastName = StringUtils.hasText(lastName) ? lastName : null;
  }
}
