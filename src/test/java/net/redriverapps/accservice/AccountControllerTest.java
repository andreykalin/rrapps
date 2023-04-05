package net.redriverapps.accservice;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import net.redriverapps.accservice.model.Account;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 *
 * @author dron
 */
@SpringBootTest
public class AccountControllerTest{

    @MockBean
    AccountRepository repository;
    
    @Autowired
    AccountController accountController;

    @Test
    public void testGetCustomerAccounts() throws Exception {
        Account account = new Account();
        account.setId(1);
        account.setAccountNumber("1234567890");
        account.setBalance(BigDecimal.valueOf(1000.00));
        account.setCurrency("USD");
        account.setLastOperationTime(LocalDateTime.now());

        List<Account> accounts = new ArrayList<>();
        accounts.add(account);

        int customerId = 1;

        Mockito.when(repository.getCustomerAccounts(customerId))
                .thenReturn(accounts);
        
        MockMvc mockMvc=MockMvcBuilders.standaloneSetup(accountController).build();
        mockMvc.perform(get("/api/customers/{customerId}/accounts", customerId))
         .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(accounts.size())))
                .andExpect(jsonPath("$[0].id", is(accounts.get(0).getId())))
                .andExpect(jsonPath("$[0].accountNumber", is(accounts.get(0).getAccountNumber())))
                .andExpect(jsonPath("$[0].balance", is(accounts.get(0).getBalance().doubleValue())))
                .andExpect(jsonPath("$[0].currency", is(accounts.get(0).getCurrency())));

    }

    @Test
    public void testGetCustomerAccounts_Error() throws Exception {

        Mockito.when(repository.getCustomerAccounts(20))
                .thenReturn(List.of());

        MockMvc mockMvc=MockMvcBuilders.standaloneSetup(accountController).build();
        mockMvc.perform(get("/api/customers/{customerId}/accounts", 20))
               .andExpect(status().isInternalServerError())
               .andExpect(jsonPath("$.message", isA(String.class)))
               .andExpect(jsonPath("$.type", is("error")));
    }

    @Test
    public void testGetCustomerAccounts_404() throws Exception {

        MockMvc mockMvc=MockMvcBuilders.standaloneSetup(accountController).build();
        mockMvc.perform(get("/some-path"))
               .andExpect(status().is(404));
    }
}
