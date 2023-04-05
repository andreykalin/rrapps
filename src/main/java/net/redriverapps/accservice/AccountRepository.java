package net.redriverapps.accservice;

import java.util.List;
import net.redriverapps.accservice.model.Account;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author dron
 */
public interface AccountRepository extends Repository<Account, Integer>{
    
        @Query(value = "select * from get_accounts(:customer_id)")
        List<Account> getCustomerAccounts(@Param("customer_id") int customerId);
}
