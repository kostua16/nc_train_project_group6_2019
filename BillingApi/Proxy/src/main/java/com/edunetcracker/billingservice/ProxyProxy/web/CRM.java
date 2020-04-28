package com.edunetcracker.billingservice.ProxyProxy.web;

import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Checks;
import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Helpers;
import com.edunetcracker.billingservice.ProxyProxy.entity.*;
import com.edunetcracker.billingservice.ProxyProxy.proxy.AccountController;
import com.edunetcracker.billingservice.ProxyProxy.proxy.HistoryController;
import com.edunetcracker.billingservice.ProxyProxy.proxy.TariffController;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQMessageType;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CRM {


    @Autowired
    TariffController tariffController;

    @Autowired
    AccountController accountController;

    @Autowired
    HistoryController historyController;

    Logger LOG = LoggerFactory.getLogger(CRM.class);




    //  http://localhost:8102/createA
    /**
     * {
     *   "login": "111",
     *   "password": "222",
     *   "name": "333",
     *   "balance": "444",
     *   "tariff": "555",
     *   "telephone": "123",
     *   "rang": "666"
     * }
     */
    @PostMapping("createA")
    public Boolean createA(@RequestBody Account newAccount) throws JsonProcessingException {
        // если нет аккаунта, но есть тариф
        LOG.info("createA {}", newAccount);
        return accountController.createAccount(newAccount);
    }

    //  http://localhost:8102/createT/?tariff=FOR_SMALL

    /**
     * {
     *   "tariff": {"tariffName":"BIG_GIB"},
     *   "tariffCall": {"Call_cost": "4.5","Call_balance": "100","Default_call_cost": "14.0"},
     *   "tariffInternet": {"Internet_cost": "4.5","Internet_balance": "100","Default_internet_cost": "14.0"},
     *   "tariffSms": {"Sms_cost": "4.5","Sms_balance": "100","Default_sms_cost": "14.0"}
     * }
     */
    @PostMapping("createT")
    public Boolean createT(@RequestBody Map<String, Map<String, String>> requestB) throws JsonProcessingException {
        final String tariffName = requestB.get("tariff").get("tariffName");
        return tariffController.createTariff(
                new CollectedTariff(tariffName,
                        new TariffCall(tariffName,
                                Float.parseFloat(requestB.get("tariffCall").get("Call_cost")),
                                Long.parseLong(requestB.get("tariffCall").get("Call_balance")),
                                Float.parseFloat(requestB.get("tariffCall").get("Default_call_cost"))),
                        new TariffInternet(tariffName,
                                Float.parseFloat(requestB.get("tariffInternet").get("Internet_cost")),
                                Long.parseLong(requestB.get("tariffInternet").get("Internet_balance")),
                                Float.parseFloat(requestB.get("tariffInternet").get("Default_internet_cost"))),
                        new TariffSms(tariffName,
                                Float.parseFloat(requestB.get("tariffSms").get("Sms_cost")),
                                Long.parseLong(requestB.get("tariffSms").get("Sms_balance")),
                                Float.parseFloat(requestB.get("tariffSms").get("Default_sms_cost")))
                ));
    }

    @DeleteMapping("searchA")
    public List<Account> searchA(@RequestParam("query") String query) {
        return accountController.searchAccounts(query);
    }
    //
    @DeleteMapping("deleteA")
    public Boolean deleteA(@RequestParam("login") String login) {
        if (accountController.isAccountExists(login)) {
            Account account = accountController.getAccount(login);
            accountController.deleteAccountByLogin(login);
            tariffController.deleteTariff(account.getTariff());
            return true;
        }
        return false;
    }

    @DeleteMapping("deleteT")
    public Boolean deleteT(@RequestParam("name") String name) {
        if (!name.equals("DEFAULT") && !name.equals("ADMINISTRATOR") && tariffController.isTariffExists(name)) {
            if (accountController.migrateToTariff(name, "DEFAULT")) {
                return tariffController.deleteTariff(name);
            }
        }
        return false;
    }
    @PostMapping("changeA")
    public Boolean changeA(@RequestBody Map<String, String> map ){
        Account account = accountController.getAccount(map.get("login"));
        if(account != null) {
            account.setPassword(map.get("password"));
            account.setName(map.get("name"));
            account.setTelephone(map.get("telephone"));
            account.setTariff(map.get("tariff"));
            accountController.updateAccount(account);
            return true;
        }
        return false;
    }

    // изменяет выбранный тариф пользователя (если был тариф А, тостанет Тафри Б) (просто меняет строку)
    @PostMapping("changeT")
    public Boolean changeT(@RequestParam("login") String login,
                           @RequestParam("tariff") String tariff){
        Account account = accountController.getAccount(login);
        if(account != null && tariffController.isTariffExists(tariff)) {
            account.setTariff(tariff);
            accountController.updateAccount(account);
            return true;
        }
        return false;
    }
    @GetMapping("showA")
    public Map<String, Object> showA(){
        List<Account> accounts = accountController.getAllAccount();
        Map<String, Map<String, String>> returnA = new HashMap<>();
        Map<String, String> acc;
        for (int a = 0; a< accounts.size(); a++){
            acc = new HashMap<>();
            acc.put("password", accounts.get(a).getPassword());
            acc.put("name", accounts.get(a).getName());
            acc.put("balance", accounts.get(a).getBalance().toString());
            acc.put("tariff", accounts.get(a).getTariff());
            acc.put("rang", accounts.get(a).getRang());
            acc.put("telephone", accounts.get(a).getTelephone());
            returnA.put(accounts.get(a).getLogin(), acc);
        }
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("accounts", returnA);
        return returnMap;
    }
    @GetMapping("getA")
    public Map<String, String> getA(@RequestParam("login") String login){

        Map<String, String> returnA = new HashMap<>();

        Account account = accountController.getAccount(login);
        if(account!=null){
            returnA.put("password", account.getPassword());
            returnA.put("name", account.getName());
            returnA.put("telephone", account.getTelephone());
            returnA.put("tariff", account.getTariff());
        }

        return returnA;
    }

    @GetMapping("showT")
    public List<Map> showT(){
        return tariffController.getAllCollectedTariffAsMapList();
    }
    //  http://localhost:8102/showHistory
    @GetMapping("showHistory")
    public List<History> showHistory(@RequestParam(value = "page", defaultValue = "0") Integer page){
        return historyController.showHistory(page);
    }
}
