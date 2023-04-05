package net.redriverapps.accservice;

import java.util.List;
import net.redriverapps.accservice.model.Account;
import net.redriverapps.accservice.model.Message;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author dron
 */
@RestController
@RequestMapping("/api/customers/{customerId}/accounts")
public class AccountController {
    private final AccountRepository accountRepository;

    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @GetMapping
    public List<Account> getCustomerAccounts(@PathVariable int customerId) {
        List<Account> res = accountRepository.getCustomerAccounts(customerId);
        if(res.isEmpty()){
            throw new IllegalArgumentException("No such customer or account list is empty");
        }
        return res;
    }
    
    //TODO more detailed exception handling
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Message processExceptions(Exception e){
        return new Message(e.getMessage(),"error");
        
    }
}