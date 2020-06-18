package com.bankstatement.batch.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Setter
@Getter
@ToString
public class CustomerUpdate {

  protected final long customerId;
}
