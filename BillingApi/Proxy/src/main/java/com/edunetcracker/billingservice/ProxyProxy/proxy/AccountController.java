package com.edunetcracker.billingservice.ProxyProxy.proxy;

import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Checks;
import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Helpers;
import com.edunetcracker.billingservice.ProxyProxy.entity.Account;
import com.edunetcracker.billingservice.ProxyProxy.entity.Call;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQMessageType;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/*@RestController*/
@Service
public class AccountController {

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @Autowired
    private Helpers helpers;

    @Autowired
    private Checks checks;
    Logger LOG = LoggerFactory.getLogger(AccountController.class);

    /////////////////////////////////////////////////////////////////////////////////////////////

    /*@GetMapping("getAccount")*/
    public ResponseEntity<Account> getAccount(/*@RequestParam("token") String token*/ String login) {
        try {
            String url = helpers.getUrlBilling() + "/getAccountByLogin/?login=" + login;
            ResponseEntity responseAccount = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Account.class);
            return responseAccount;
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>( null, HttpStatus.NOT_FOUND);
        }
    }
    /*@GetMapping("getAccountByLogin")*/
    public ResponseEntity<Account> getAccountForName(/*@RequestParam("token") String token,*/
                                                     /*@RequestParam("login")*/ String login ) {
        try {
            String url = helpers.getUrlBilling() + "/getAccountByLogin/?login=" + login;
            ResponseEntity<Account> responseAccount = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Account.class);
            return responseAccount;
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>( null, HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<Account> getAccountByTelephone(String telephone) {
        try {
            LOG.info("getAccountByTelephone");
            String url = helpers.getUrlBilling() + "/getAccountByTelephone/?telephone=" + telephone;
            ResponseEntity<Account> responseAccount = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Account.class);
            return responseAccount;
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>( null, HttpStatus.NOT_FOUND);
        }
    }
    /*@GetMapping("getAllAccount")*/
    public ResponseEntity<List<Account>> getAllAccount(/*@RequestParam("token") String token*/) {
        try {
            String url = helpers.getUrlBilling() + "/getAllAccount";
            ResponseEntity <List<Account>> responseAccount = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), new ParameterizedTypeReference<List<Account>>() {});
            return responseAccount;
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>( null, HttpStatus.NOT_FOUND);
        }
    }

    /*@PostMapping("createAccount")*/
    public Boolean createAccount(/*@RequestParam("token") String token,
                                 @RequestBody*/ Account account) {
        try {
            rabbitMQSender.send(account, RabbitMQMessageType.CREATE_ACCOUNT);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*@PostMapping("createAccountWithoutToken")*/
    public Boolean createAccountWithoutToken(/*@RequestBody */Account account) {
        try {
            rabbitMQSender.send(account, RabbitMQMessageType.CREATE_ACCOUNT);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*@PutMapping("updateAccount")*/
    public Boolean updateAccount(/*@RequestParam("token") String token,
                                 @RequestBody */Account newAccountData) {
        try {
            rabbitMQSender.send(newAccountData, RabbitMQMessageType.UPDATE_ACCOUNT);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /*@DeleteMapping("deleteAccountByLogin")*/
    public Boolean deleteAccountByLogin(/*@RequestParam("token") String token,
                                        @RequestParam("login")*/ String login) {

        try {
            rabbitMQSender.send(login, RabbitMQMessageType.DELETE_ACCOUNT);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
