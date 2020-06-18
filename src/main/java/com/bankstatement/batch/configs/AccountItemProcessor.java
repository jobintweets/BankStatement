package com.bankstatement.batch.configs;

import com.bankstatement.batch.domain.Statement;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
//here we will be getting each customer object form database
//the statement class has the customer object in it
// in here we fetch all the accounts and respective transactions
public class AccountItemProcessor implements ItemProcessor<Statement, Statement> {

  @Autowired
  private final JdbcTemplate jdbcTemplate;

  public AccountItemProcessor(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Statement process(Statement statement) throws Exception {
    statement.setAccounts(this.jdbcTemplate.query(
      "select a.account_id," +
        "       a.balance," +
        "       a.last_statement_date," +
        "       t.transaction_id," +
        "       t.description," +
        "       t.credit," +
        "       t.debit," +
        "       t.timestamp " +
				"from account a left join " +  //MYSQL
				"    transaction t on a.account_id = t.account_account_id " +
        "where a.account_id in " +
        "	(select account_account_id " +
        "	from customer_account " +
        "	where customer_customer_id = ?) " +
        "order by t.timestamp",
      new Object[]{statement.getCustomer().getId()},
//      We’ll use a ResultSetExtractor because the query we’ll be running results
//      in a parent child relationship with one account having many transactions.
      new AccountResultSetExtractor()));
    return statement;
  }
}
