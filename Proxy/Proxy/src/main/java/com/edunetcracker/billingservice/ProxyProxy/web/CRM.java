package com.edunetcracker.billingservice.ProxyProxy.web;

import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Checks;
import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Helpers;
import com.edunetcracker.billingservice.ProxyProxy.entity.*;
import com.edunetcracker.billingservice.ProxyProxy.proxy.AccountController;
import com.edunetcracker.billingservice.ProxyProxy.proxy.TariffController;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQMessageType;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CRM {

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @Autowired
    private Helpers helpers;

    @Autowired
    private Checks checks;

    @Autowired
    TariffController tariffController;

    @Autowired
    AccountController accountController;

    /**
     * admin@mail.ru
     *
     * user@mail.ru
     *
     * 123456
     * Номер 88005553535 у второго 88005553536
     */
    //  http://localhost:8102/start
    @PostMapping("start")
    public Boolean start() throws JsonProcessingException {
        if (!checks.isTariffExists("DEFAULT")) {
            Tariff tariff = new Tariff();
            tariff.setName("DEFAULT");
            rabbitMQSender.send(tariff, RabbitMQMessageType.CREATE_TARIFF);
            TariffCall tariffCall = new TariffCall();
            tariffCall.setName("DEFAULT");
            tariffCall.setCall_cost(2.2F);
            tariffCall.setCall_balance(10000L);
            tariffCall.setDefault_call_cost(3.5F);
            rabbitMQSender.send(tariffCall, RabbitMQMessageType.CREATE_TARIFF_CALL);
            TariffInternet tariffInternet = new TariffInternet();
            tariffInternet.setName("DEFAULT");
            tariffInternet.setInternet_cost(2.2F);
            tariffInternet.setInternet_balance(20000L);
            tariffInternet.setDefault_internet_cost(3.5F);
            rabbitMQSender.send(tariffInternet, RabbitMQMessageType.CREATE_TARIFF_INTERNET);
            TariffSms tariffSms = new TariffSms();
            tariffSms.setName("DEFAULT");
            tariffSms.setSms_cost(2.2F);
            tariffSms.setSms_balance(300L);
            tariffSms.setDefault_sms_cost(3.5F);
            rabbitMQSender.send(tariffSms, RabbitMQMessageType.CREATE_TARIFF_SMS);
        }
        if (!checks.isTariffExists("ADMINISTRATOR")) {
            Tariff tariff = new Tariff();
            tariff.setName("ADMINISTRATOR");
            rabbitMQSender.send(tariff, RabbitMQMessageType.CREATE_TARIFF);
            TariffCall tariffCall = new TariffCall();
            tariffCall.setName("ADMINISTRATOR");
            tariffCall.setCall_cost(0F);
            tariffCall.setCall_balance(0L);
            tariffCall.setDefault_call_cost(0F);
            rabbitMQSender.send(tariffCall, RabbitMQMessageType.CREATE_TARIFF_CALL);
            TariffInternet tariffInternet = new TariffInternet();
            tariffInternet.setName("ADMINISTRATOR");
            tariffInternet.setInternet_cost(0F);
            tariffInternet.setInternet_balance(0L);
            tariffInternet.setDefault_internet_cost(0F);
            rabbitMQSender.send(tariffInternet, RabbitMQMessageType.CREATE_TARIFF_INTERNET);
            TariffSms tariffSms = new TariffSms();
            tariffSms.setName("ADMINISTRATOR");
            tariffSms.setSms_cost(0F);
            tariffSms.setSms_balance(0L);
            tariffSms.setDefault_sms_cost(0F);
            rabbitMQSender.send(tariffSms, RabbitMQMessageType.CREATE_TARIFF_SMS);
        }
        if (!checks.isAccountExists("admin@mail.ru")) {
            Account account = new Account();
            account.setLogin("admin@mail.ru");
            account.setPassword("123456");
            account.setName("admin");
            account.setBalance(0L);
            account.setTariff("ADMINISTRATOR");
            account.setTelephone("88005553535");
            account.setRang("ADMINISTRATOR");
            rabbitMQSender.send(account, RabbitMQMessageType.CREATE_ACCOUNT);

            Call call = new Call();
            call.setLogin("admin@mail.ru");
            call.setCall_cost(0F);
            call.setCall_balance(0L);
            call.setDefault_call_cost(0F);
            rabbitMQSender.send(call, RabbitMQMessageType.CREATE_CALL);
            Internet internet = new Internet();
            internet.setLogin("admin@mail.ru");
            internet.setInternet_cost(0F);
            internet.setInternet_balance(0L);
            internet.setDefault_internet_cost(0F);
            rabbitMQSender.send(internet, RabbitMQMessageType.CREATE_INTERNET);
            Sms sms = new Sms();
            sms.setLogin("admin@mail.ru");
            sms.setSms_cost(0F);
            sms.setSms_balance(0L);
            sms.setDefault_sms_cost(0F);
            rabbitMQSender.send(sms, RabbitMQMessageType.CREATE_SMS);
        }
        if (!checks.isAccountExists("user@mail.ru")){
            Account account = new Account();
            account.setLogin("user@mail.ru");
            account.setPassword("123456");
            account.setName("user");
            account.setBalance(0L);
            account.setTariff("DEFAULT");
            account.setTelephone("88005553536");
            account.setRang("USER");
            rabbitMQSender.send(account, RabbitMQMessageType.CREATE_ACCOUNT);
            // взять тариф и присвоить его
            Call call = new Call();
            call.setLogin("user@mail.ru");
            call.setCall_cost(2.2F);
            call.setCall_balance(10000L);
            call.setDefault_call_cost(3.5F);
            rabbitMQSender.send(call, RabbitMQMessageType.CREATE_CALL);
            Internet internet = new Internet();
            internet.setLogin("user@mail.ru");
            internet.setInternet_cost(2.2F);
            internet.setInternet_balance(20000L);
            internet.setDefault_internet_cost(3.5F);
            rabbitMQSender.send(internet, RabbitMQMessageType.CREATE_INTERNET);
            Sms sms = new Sms();
            sms.setLogin("user@mail.ru");
            sms.setSms_cost(2.2F);
            sms.setSms_balance(300L);
            sms.setDefault_sms_cost(3.5F);
            rabbitMQSender.send(sms, RabbitMQMessageType.CREATE_SMS);

        }
        return true;
    }

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
        System.out.println("createA");
        if (!checks.isAccountExists(newAccount.getLogin()) && checks.isTariffExists(newAccount.getTariff())) {
            System.out.println("createA !");
            Account account = newAccount;
            account.setBalance(0L);
            //account.setRang("base_user");
            rabbitMQSender.send(account, RabbitMQMessageType.CREATE_ACCOUNT);
            CollectedTariff collectedTariff = tariffController.getCollectedTariffByName(newAccount.getTariff()).getBody();
            Call call = new Call();
            call.setLogin(newAccount.getLogin());
            call.setCall_cost(collectedTariff.getTariffCall().getCall_cost());
            call.setCall_balance(collectedTariff.getTariffCall().getCall_balance());
            call.setDefault_call_cost(collectedTariff.getTariffCall().getDefault_call_cost());
            rabbitMQSender.send(call, RabbitMQMessageType.CREATE_CALL);
            Internet internet = new Internet();
            internet.setLogin(newAccount.getLogin());
            internet.setInternet_cost(collectedTariff.getTariffInternet().getInternet_cost());
            internet.setInternet_balance(collectedTariff.getTariffInternet().getInternet_balance());
            internet.setDefault_internet_cost(collectedTariff.getTariffInternet().getDefault_internet_cost());
            rabbitMQSender.send(internet, RabbitMQMessageType.CREATE_INTERNET);
            Sms sms = new Sms();
            sms.setLogin(newAccount.getLogin());
            sms.setSms_cost(collectedTariff.getTariffSms().getSms_cost());
            sms.setSms_balance(collectedTariff.getTariffSms().getSms_balance());
            sms.setDefault_sms_cost(collectedTariff.getTariffSms().getDefault_sms_cost());
            rabbitMQSender.send(sms, RabbitMQMessageType.CREATE_SMS);
            return true;
        }
        return false;
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
        if (!checks.isTariffExists(requestB.get("tariff").get("tariffName"))) {
            Tariff tariff = new Tariff();
            tariff.setName(requestB.get("tariff").get("tariffName"));
            rabbitMQSender.send(tariff, RabbitMQMessageType.CREATE_TARIFF);
            TariffCall tariffCall = new TariffCall();
            tariffCall.setName(requestB.get("tariff").get("tariffName"));
            tariffCall.setCall_cost(Float.parseFloat(requestB.get("tariffCall").get("Call_cost")));
            tariffCall.setCall_balance(Long.parseLong(requestB.get("tariffCall").get("Call_balance")));
            tariffCall.setDefault_call_cost(Float.parseFloat(requestB.get("tariffCall").get("Default_call_cost")));
            rabbitMQSender.send(tariffCall, RabbitMQMessageType.CREATE_TARIFF_CALL);
            TariffInternet tariffInternet = new TariffInternet();
            tariffInternet.setName(requestB.get("tariff").get("tariffName"));
            tariffInternet.setInternet_cost(Float.parseFloat(requestB.get("tariffInternet").get("Internet_cost")));
            tariffInternet.setInternet_balance(Long.parseLong(requestB.get("tariffInternet").get("Internet_balance")));
            tariffInternet.setDefault_internet_cost(Float.parseFloat(requestB.get("tariffInternet").get("Default_internet_cost")));
            rabbitMQSender.send(tariffInternet, RabbitMQMessageType.CREATE_TARIFF_INTERNET);
            TariffSms tariffSms = new TariffSms();
            tariffSms.setName(requestB.get("tariff").get("tariffName"));
            tariffSms.setSms_cost(Float.parseFloat(requestB.get("tariffSms").get("Sms_cost")));
            tariffSms.setSms_balance(Long.parseLong(requestB.get("tariffSms").get("Sms_balance")));
            tariffSms.setDefault_sms_cost(Float.parseFloat(requestB.get("tariffSms").get("Default_sms_cost")));
            rabbitMQSender.send(tariffSms, RabbitMQMessageType.CREATE_TARIFF_SMS);
            return true;
        }
        return false;
    }

    //
    @DeleteMapping("deleteA")
    public Boolean deleteA(@RequestParam("login") String login) throws JsonProcessingException {
        if (checks.isAccountExists(login)) {
            Account account = accountController.getAccount(login).getBody();
            accountController.deleteAccountByLogin(login);
            tariffController.deleteCollectedTariffByName(account.getTariff());
            return true;
        }
        return false;
    }

    @DeleteMapping("deleteT")
    public Boolean deleteT(@RequestParam("name") String name) throws JsonProcessingException {
        if (checks.isTariffExists(name)) {
            tariffController.deleteTariff(name);
            return true;
        }
        return false;
    }

    // изменяет выбранный тариф пользователя (если был тариф А, тостанет Тафри Б) (просто меняет строку)
    @PostMapping("changeT")
    public Boolean changeT(@RequestParam("login") String login,
                           @RequestParam("tariff") String tariff){
        Account account = accountController.getAccount(login).getBody();
        if(account != null) {
            account.setTariff(tariff);
            accountController.updateAccount(account);
            return true;
        }
        return false;
    }
    @GetMapping("showA")
    public Map<String, Object> showA(){
        List<Account> accounts = accountController.getAllAccount().getBody();
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
    @GetMapping("showT")
    public Map<String, Object> showT(){
        List<CollectedTariff> tariffs = tariffController.getAllCollectedTariff().getBody();
        Map<String, Map<String, String>> returnT = new HashMap<>();
        Map<String, String> tariff;
        for (int a = 0; a< tariffs.size(); a++){
            tariff = new HashMap<>();
            tariff.put("call", tariffs.get(a).getTariffCall().getCall_balance().toString());
            tariff.put("internet", tariffs.get(a).getTariffInternet().getInternet_balance().toString());
            tariff.put("sms", tariffs.get(a).getTariffSms().getSms_balance().toString());
            returnT.put(tariffs.get(a).getName(), tariff);
        }
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("tariffs", returnT);
        return returnMap;
    }
    //  http://localhost:8102/showHistory
    @GetMapping("showHistory")
    public List<History> showHistory(){
        String url = helpers.getUrlBilling() + "/getAllHistory";
        List<History> response = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), List.class).getBody();
        return response;
    }
}
