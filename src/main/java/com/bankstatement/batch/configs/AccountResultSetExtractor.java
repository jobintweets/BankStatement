package com.bankstatement.batch.configs;

import com.bankstatement.batch.domain.Account;
import com.bankstatement.batch.domain.Transaction;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// we have a parent child relationship between Accounts and Transactions.
// Account class has List of Transactions.
// This control is then passed to the itemWriter
public class AccountResultSetExtractor implements ResultSetExtractor<List<Account>> {

  private List<Account> accounts = new ArrayList<>();
  private Account curAccount;

  @Override
  public List<Account> extractData(ResultSet rs) throws SQLException, DataAccessException {
    while (rs.next()) {
      if (curAccount == null) {
        curAccount = new Account(
          rs.getLong("account_id"),
          rs.getBigDecimal("balance"),
          rs.getDate("last_statement_date"));
      } else if (rs.getLong("account_id") != curAccount.getId()) {
        accounts.add(curAccount);
        curAccount = new Account(
          rs.getLong("account_id"),
          rs.getBigDecimal("balance"),
          rs.getDate("last_statement_date"));
      }

      //description is coming from Transactions
      if (StringUtils.hasText(rs.getString("description"))) {
        curAccount.addTransaction(
          new Transaction(rs.getLong("transaction_id"),
            rs.getLong("account_id"),
            rs.getString("description"),
            rs.getBigDecimal("credit"),
            rs.getBigDecimal("debit"),
            new Date(rs.getTimestamp("timestamp").getTime())));
      }
    }

    if (curAccount != null) {
      accounts.add(curAccount);
    }
    return accounts;
//    results are returned to the itemProcessor
  }
}
/***
 * If the current Account is null or the account id does not equal the current one,
 * we’ll create a new Account objecg
 *  Once we have the Account object, for each record that has a transaction, we add a Transaction object.
 * This allows us to build up a list of Account objects that we return to the ItemProcessor which adds them to the Statement item to be written.
 */
