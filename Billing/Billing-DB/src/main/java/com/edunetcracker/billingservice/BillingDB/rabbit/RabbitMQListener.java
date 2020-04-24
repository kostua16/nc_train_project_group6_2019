package com.edunetcracker.billingservice.BillingDB.rabbit;

import com.edunetcracker.billingservice.BillingDB.entity.*;
import com.edunetcracker.billingservice.BillingDB.services.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.edunetcracker.billingservice.BillingDB.rabbit.RabbitMQMessageType.*;

@EnableRabbit
@Service
public class RabbitMQListener {

    @Autowired
    IAccountRepository AccountRepository = null;

    @Autowired
    ICallRepository CallRepository = null;

    @Autowired
    IInternetRepository InternetRepository = null;

    @Autowired
    ISmsRepository SmsRepository = null;

    @Autowired
    ITariffRepository TariffRepository = null;

    @Autowired
    ITariffCallRepository TariffCallRepository = null;

    @Autowired
    ITariffInternetRepository TariffInternetRepository = null;

    @Autowired
    ITariffSmsRepository TariffSmsRepository = null;

    @Autowired
    IHistoryRepository historyRepository = null;

    @Autowired
    ObjectMapper objectMapper;

    @RabbitListener(queues = "q2")
    public void processQueue2(Message message) {
        try {
            switch (message.getMessageProperties().getType()) {

                /** Account */
                case CREATE_ACCOUNT: {  //message = Account
                    System.out.println("CREATE_ACCOUNT");
                    Account account = objectMapper.readValue(message.getBody(), Account.class);
                    System.out.println(account.getLogin() + "  " + account.getBalance());
                    AccountRepository.save(account);
                    addToHistory(CREATE_ACCOUNT, objectMapper.writeValueAsString(account));
                    break;
                }
                case DELETE_ACCOUNT: {  //message = String
                    System.out.println("DELETE_ACCOUNT");
                    String accountLogin = objectMapper.readValue(message.getBody(), String.class);
                    AccountRepository.deleteByLogin(accountLogin);
                    addToHistory(DELETE_ACCOUNT, objectMapper.writeValueAsString(accountLogin));
                    break;
                }
                case UPDATE_ACCOUNT: {  //message = Account
                    System.out.println("UPDATE_ACCOUNT");
                    Account account = objectMapper.readValue(message.getBody(), Account.class);
                    AccountRepository.updateAccount(account);
                    addToHistory(UPDATE_ACCOUNT, objectMapper.writeValueAsString(account));
                    break;
                }
                /** Balance */

                case ADD_BALANCE: { //message = Account
                    System.out.println("ADD_BALANCE");
                    Account account = objectMapper.readValue(message.getBody(), Account.class);
                    Account realAccount = AccountRepository.findAccountByLogin(account.getLogin());
                    account.setBalance(account.getBalance() + realAccount.getBalance());
                    AccountRepository.addAccountBalance(account);
                    addToHistory(ADD_BALANCE, objectMapper.writeValueAsString(account));
                    break;
                }
                /** Call */

                case CREATE_CALL: { //message = Call
                    System.out.println("CREATE_CALL");
                    Call call = objectMapper.readValue(message.getBody(), Call.class);
                    CallRepository.save(call);
                    addToHistory(CREATE_CALL, objectMapper.writeValueAsString(call));
                    break;
                }
                case DELETE_CALL: { //message = String
                    System.out.println("DELETE_CALL");
                    String login = objectMapper.readValue(message.getBody(), String.class);
                    CallRepository.deleteByLogin(login);
                    addToHistory(DELETE_CALL, objectMapper.writeValueAsString(login));
                    break;
                }
                case UPDATE_CALL: { //message = Call
                    System.out.println("UPDATE_CALL");
                    Call call = objectMapper.readValue(message.getBody(), Call.class);
                    CallRepository.updateCall(call);
                    addToHistory(UPDATE_CALL, objectMapper.writeValueAsString(call));
                    break;
                }
                case CALL_ONE_MINUTE: { //message = Call
                    System.out.println("CALL_ONE_SECOND");
                    Call call = objectMapper.readValue(message.getBody(), Call.class);
                    Call realCall = CallRepository.findCallByLogin(call.getLogin());
                    realCall.setCall_balance(realCall.getCall_balance() - call.getCall_balance());
                    CallRepository.updateCall(realCall);
                    addToHistory(CALL_ONE_MINUTE, objectMapper.writeValueAsString(call));
                    break;
                }
                case CALL_ONE_SECOND: { //message = Call
                    System.out.println("CALL_ONE_SECOND");
                    Call call = objectMapper.readValue(message.getBody(), Call.class);
                    Call realCall = CallRepository.findCallByLogin(call.getLogin());
                    realCall.setCall_balance(realCall.getCall_balance() - call.getCall_balance());
                    CallRepository.updateCall(realCall);
                    addToHistory(CALL_ONE_SECOND, objectMapper.writeValueAsString(call));
                    break;
                }
                case STOP_CALL: {
                    System.out.println("STOP_CALL");
                    addToHistory(STOP_CALL, "");
                    break;
                }

                /** Internet */

                case CREATE_INTERNET: { //message = Internet
                    System.out.println("CREATE_INTERNET");
                    Internet internet = objectMapper.readValue(message.getBody(), Internet.class);
                    InternetRepository.save(internet);
                    addToHistory(CREATE_INTERNET, objectMapper.writeValueAsString(internet));
                    break;
                }
                case DELETE_INTERNET: { //message = String
                    System.out.println("DELETE_INTERNET");
                    String login = objectMapper.readValue(message.getBody(), String.class);
                    InternetRepository.deleteByLogin(login);
                    addToHistory(DELETE_INTERNET, objectMapper.writeValueAsString(login));
                    break;
                }
                case UPDATE_INTERNET: { //message = Call
                    System.out.println("UPDATE_INTERNET");
                    Internet internet = objectMapper.readValue(message.getBody(), Internet.class);
                    InternetRepository.updateInternet(internet);
                    addToHistory(UPDATE_INTERNET, objectMapper.writeValueAsString(internet));
                    break;
                }
                case INTERNET_USE_KILOBYTE: { //message = Call
                    System.out.println("INTERNET_USE_KILOBYTE");
                    Internet internet = objectMapper.readValue(message.getBody(), Internet.class);
                    Internet realInternet = InternetRepository.findInternetByLogin(internet.getLogin());
                    realInternet.setInternet_balance(realInternet.getInternet_balance() - internet.getInternet_balance());
                    InternetRepository.updateInternet(realInternet);
                    addToHistory(INTERNET_USE_KILOBYTE, objectMapper.writeValueAsString(internet));
                    break;
                }
                /** Sms */

                case CREATE_SMS: {
                    System.out.println("CREATE_SMS");
                    Sms sms = objectMapper.readValue(message.getBody(), Sms.class);
                    SmsRepository.save(sms);
                    addToHistory(CREATE_SMS, objectMapper.writeValueAsString(sms));
                    break;
                }
                case DELETE_SMS: {
                    System.out.println("DELETE_SMS");
                    String login = objectMapper.readValue(message.getBody(), String.class);
                    SmsRepository.deleteByLogin(login);
                    addToHistory(DELETE_SMS, objectMapper.writeValueAsString(login));
                    break;
                }
                case UPDATE_SMS: {
                    System.out.println("UPDATE_SMS");
                    Sms sms = objectMapper.readValue(message.getBody(), Sms.class);
                    SmsRepository.updateSms(sms);
                    addToHistory(UPDATE_SMS, objectMapper.writeValueAsString(sms));
                    break;
                }

                case REQUEST_SMS: {
                    System.out.println("REQUEST_SMS");
                    Sms sms = objectMapper.readValue(message.getBody(), Sms.class);
                    Sms realSms = SmsRepository.findSmsByLogin(sms.getLogin());
                    realSms.setSms_balance(realSms.getSms_balance() - sms.getSms_balance());
                    SmsRepository.updateSms(realSms);
                    addToHistory(REQUEST_SMS, objectMapper.writeValueAsString(sms));
                    break;
                }
                /** Tariff */
                case CREATE_TARIFF: {
                    System.out.println("CREATE_TARIFF");
                    Tariff tariff = objectMapper.readValue(message.getBody(), Tariff.class);
                    TariffRepository.save(tariff);
                    addToHistory(CREATE_TARIFF, objectMapper.writeValueAsString(tariff));
                    break;
                }
                case DELETE_TARIFF: {
                    System.out.println("DELETE_TARIFF");
                    String login = objectMapper.readValue(message.getBody(), String.class);
                    TariffRepository.deleteTariffByName(login);
                    addToHistory(DELETE_TARIFF, objectMapper.writeValueAsString(login));
                    break;
                }
                /** Tariff Call */
                case CREATE_TARIFF_CALL: {  //message = Account
                    System.out.println("CREATE_TARIFF_CALL");
                    TariffCall t = objectMapper.readValue(message.getBody(), TariffCall.class);
                    TariffCallRepository.save(t);
                    addToHistory(CREATE_TARIFF_CALL, objectMapper.writeValueAsString(t));
                    break;
                }
                case DELETE_TARIFF_CALL: {  //message = String
                    System.out.println("DELETE_TARIFF_CALL");
                    String name = objectMapper.readValue(message.getBody(), String.class);
                    TariffCallRepository.deleteByName(name);
                    addToHistory(DELETE_TARIFF_CALL, objectMapper.writeValueAsString(name));
                    break;
                }
                case UPDATE_TARIFF_CALL: {  //message = Account
                    System.out.println("UPDATE_TARIFF_CALL");
                    TariffCall t = objectMapper.readValue(message.getBody(), TariffCall.class);
                    TariffCallRepository.updateTariffCall(t);
                    addToHistory(UPDATE_TARIFF_CALL, objectMapper.writeValueAsString(t));
                    break;
                }
                /** Tariff Internet */
                case CREATE_TARIFF_INTERNET: {  //message = Account
                    System.out.println("CREATE_TARIFF_INTERNET");
                    TariffInternet t = objectMapper.readValue(message.getBody(), TariffInternet.class);
                    TariffInternetRepository.save(t);
                    addToHistory(CREATE_TARIFF_INTERNET, objectMapper.writeValueAsString(t));
                    break;
                }
                case DELETE_TARIFF_INTERNET: {  //message = String
                    System.out.println("DELETE_TARIFF_INTERNET");
                    String name = objectMapper.readValue(message.getBody(), String.class);
                    TariffInternetRepository.deleteByName(name);
                    addToHistory(DELETE_TARIFF_INTERNET, objectMapper.writeValueAsString(name));
                    break;
                }
                case UPDATE_TARIFF_INTERNET: {  //message = Account
                    System.out.println("UPDATE_TARIFF_INTERNET");
                    TariffInternet t = objectMapper.readValue(message.getBody(), TariffInternet.class);
                    TariffInternetRepository.updateTariffInternet(t);
                    addToHistory(UPDATE_TARIFF_INTERNET, objectMapper.writeValueAsString(t));
                    break;
                }
                /** Tariff Call */
                case CREATE_TARIFF_SMS: {  //message = Account
                    System.out.println("CREATE_TARIFF_SMS");
                    TariffSms t = objectMapper.readValue(message.getBody(), TariffSms.class);
                    TariffSmsRepository.save(t);
                    addToHistory(CREATE_TARIFF_SMS, objectMapper.writeValueAsString(t));
                    break;
                }
                case DELETE_TARIFF_SMS: {  //message = String
                    System.out.println("DELETE_TARIFF_SMS");
                    String name = objectMapper.readValue(message.getBody(), String.class);
                    TariffSmsRepository.deleteByName(name);
                    addToHistory(DELETE_TARIFF_SMS, objectMapper.writeValueAsString(name));
                    break;
                }
                case UPDATE_TARIFF_SMS: {  //message = Account
                    System.out.println("UPDATE_TARIFF_SMS");
                    TariffSms t = objectMapper.readValue(message.getBody(), TariffSms.class);
                    TariffSmsRepository.updateTariffSms(t);
                    addToHistory(UPDATE_TARIFF_SMS, objectMapper.writeValueAsString(t));
                    break;
                }
                default: {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void addToHistory(String type, String body){
        String time = LocalDate.now().toString() + " " + LocalTime.now().toString();
        History history = new History(time, type, body);
        System.out.println("addToHistory " + type);
        //History history = new History();//new History(LocalDate.now().toString(), type, body);
        historyRepository.save(history);
    }

}
