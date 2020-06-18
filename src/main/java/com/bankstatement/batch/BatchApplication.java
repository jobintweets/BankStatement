package com.bankstatement.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class BatchApplication {

  public static void main(String[] args) {
    SpringApplication.run(BatchApplication.class,
      "customerUpdateFile=file:/Users/jobingeorge/Desktop/BankStatement/src/main/resources/input/customer_update.csv",
      "transactionFile=file:/Users/jobingeorge/Desktop/BankStatement/src/main/resources/input/transactions.xml");
  }

}
