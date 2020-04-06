package com.edunetcracker.billingservice.ProxyProxy.proxy;

import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Checks;
import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Helpers;
import com.edunetcracker.billingservice.ProxyProxy.entity.Account;
import com.edunetcracker.billingservice.ProxyProxy.entity.Call;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQMessageType;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQSender;
import com.edunetcracker.billingservice.ProxyProxy.session.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class AccountController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @Autowired
    private Helpers helpers;

    @Autowired
    private Checks checks;

    /////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping("getAccount")
    public ResponseEntity<Account> getAccount(@RequestParam("token") String token ) {
        try {
            if (sessionService.inSession(token)) {
                String login = sessionService.getLogin(token).getLogin();
                if (checks.isAccountExists(login)) {
                    String url = helpers.getUrlBilling() + "/getAccountByLogin/?login=" + login;
                    ResponseEntity responseAccount = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Account.class);
                    return responseAccount;
                }
            }
            return new ResponseEntity<>( null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>( null, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("getAccountByLogin")
    public ResponseEntity<Account> getAccountForName(@RequestParam("token") String token,
                                                     @RequestParam("login") String login ) {
        try {
            if(sessionService.inSession(token)) {
                if (checks.isAccountExists(login)) {
                    String url = helpers.getUrlBilling() + "/getAccountByLogin/?login=" + login;
                    ResponseEntity<Account> responseAccount = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Account.class);
                    return responseAccount;
                }
            }
            return new ResponseEntity<>( null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>( null, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("getAllAccount")
    public ResponseEntity<List<Account>> getAllAccount(@RequestParam("token") String token ) {
        try {
            if (sessionService.inSession(token)) {
                    String url = helpers.getUrlBilling() + "/getAllAccount";
                    ResponseEntity <List<Account>> responseAccount = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), new ParameterizedTypeReference<List<Account>>() {});
                    return responseAccount;
            }
            return new ResponseEntity<>( null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>( null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("createAccount")
    public Boolean createAccount(@RequestParam("token") String token,
                                 @RequestBody Account account) {
        try {
            if(sessionService.inSession(token)) {
                String login = sessionService.getLogin(token).getLogin();
                if (checks.isAccountExists(login)) {
                    // AnotherFunctionWithCreateAccount(login);
                    rabbitMQSender.send(account, RabbitMQMessageType.CREATE_ACCOUNT);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @PostMapping("createAccountWithoutToken")
    public Boolean createAccountWithoutToken(@RequestBody Account account) {
        try {
            System.out.println(account.getLogin() + "  "+ account.getBalance());
            rabbitMQSender.send(account, RabbitMQMessageType.CREATE_ACCOUNT);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @PutMapping("updateAccount")
    public Boolean updateAccount(@RequestParam("token") String token,
                                 @RequestBody Account newAccountData) {
        try {
            if(sessionService.inSession(token)) {
                String login = newAccountData.getLogin();
                if (checks.isAccountExists(login)) {
                    rabbitMQSender.send(newAccountData, RabbitMQMessageType.UPDATE_ACCOUNT);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @DeleteMapping("deleteAccount")
    public Boolean deleteAccount(@RequestParam("token") String token) {
        try {
            if(sessionService.inSession(token)) {
                String login = sessionService.getLogin(token).getLogin();
                if (checks.isAccountExists(login)) {
                    rabbitMQSender.send(login, RabbitMQMessageType.DELETE_ACCOUNT);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @DeleteMapping("deleteAccountByLogin")
    public Boolean deleteAccountByLogin(@RequestParam("token") String token,
                                        @RequestParam("login") String login) {

        try {
            if(sessionService.inSession(token)) {
                if (checks.isAccountExists(login)) {
                    rabbitMQSender.send(login, RabbitMQMessageType.DELETE_ACCOUNT);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void AnotherFunctionWithCreateAccount(String login){
        String url;
        /****Call***/
        url = helpers.getUrlProxy() + "/createCall";
        Call createNewCall = new Call();
        createNewCall.defaultCall(login);
        new RestTemplate().exchange(url, HttpMethod.POST, new HttpEntity<>(createNewCall, new HttpHeaders()), Boolean.class);
    }
}
