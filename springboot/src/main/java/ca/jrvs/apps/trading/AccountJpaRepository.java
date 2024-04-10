package ca.jrvs.apps.trading;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountJpaRepository extends JpaRepository<Account, Integer> {

    Account getAccountByTraderId(Integer traderId);

    @Query("select a from Account a where a.trader_id = ?1")
    Account getAccountByTraderUsingJpql(Integer traderId);

    
}