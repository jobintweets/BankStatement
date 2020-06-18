package com.bankstatement.batch.configs;

import com.bankstatement.batch.domain.CustomerUpdate;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.Map;

/***
 * r. The intent of this component will be to look up the customer id in the CustomerUpdate object it receives.
 * If it exists in the database, we’ll let the record through.
 * If it does not exist, we’ll filter those records out.
 */
// connected with the itemProcessor
@Component
public class CustomerItemValidator implements Validator<CustomerUpdate> {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  protected static final String FIND_CUSTOMER = "SELECT COUNT(*) FROM CUSTOMER WHERE customer_id = :id";

  public CustomerItemValidator(DataSource dataSource) {
    this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
  }

  @Override
  public void validate(CustomerUpdate customer) throws ValidationException {
    Map<String, Long> parameterMap = Collections.singletonMap("id", customer.getCustomerId());
    Long count = jdbcTemplate.queryForObject(FIND_CUSTOMER, parameterMap, Long.class);
    if(count != null && count == 0) {
      throw new ValidationException(String.format("Customer id %s was not able to be found", customer.getCustomerId()));
    }
  }
}
