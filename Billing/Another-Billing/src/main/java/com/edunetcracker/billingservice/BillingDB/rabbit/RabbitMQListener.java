package com.edunetcracker.billingservice.BillingDB.rabbit;

import com.edunetcracker.billingservice.BillingDB.entity.Account;
import com.edunetcracker.billingservice.BillingDB.entity.Call;
import com.edunetcracker.billingservice.BillingDB.entity.Internet;
import com.edunetcracker.billingservice.BillingDB.entity.Sms;
import com.edunetcracker.billingservice.BillingDB.services.IAccountRepository;
import com.edunetcracker.billingservice.BillingDB.services.ICallRepository;
import com.edunetcracker.billingservice.BillingDB.services.IInternetRepository;
import com.edunetcracker.billingservice.BillingDB.services.ISmsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

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
    ObjectMapper objectMapper;

    @RabbitListener(queues = "q2")
    public void processQueue2(Message message) {
        try {
            switch (message.getMessageProperties().getType()) {

                /** Account */
                case CREATE_ACCOUNT: {  //message = Account
                    System.out.println("CREATE_ACCOUNT");
                    Account account = objectMapper.readValue(message.getBody(), Account.class);
                    AccountRepository.save(account);
                    break;
                }
                case DELETE_ACCOUNT: {  //message = String
                    System.out.println("DELETE_ACCOUNT");
                    String accountLogin = objectMapper.readValue(message.getBody(), String.class);
                    AccountRepository.deleteByLogin(accountLogin);
                    break;
                }
                case UPDATE_ACCOUNT: {  //message = Account
                    System.out.println("UPDATE_ACCOUNT");
                    Account account = objectMapper.readValue(message.getBody(), Account.class);
                    AccountRepository.updateAccount(account);
                    break;
                }
                /** Balance */

                case ADD_BALANCE: { //message = Account
                    System.out.println("ADD_BALANCE");
                    Account account = objectMapper.readValue(message.getBody(), Account.class);
                    Account realAccount = AccountRepository.findAccountByLogin(account.getLogin());
                    account.setBalance(account.getBalance()+realAccount.getBalance());
                    AccountRepository.addAccountBalance(account);
                    break;
                }
                /** Call */

                case CREATE_CALL: { //message = Call
                    System.out.println("CREATE_CALL");
                    Call call = objectMapper.readValue(message.getBody(), Call.class);
                    CallRepository.save(call);
                    break;
                }
                case DELETE_CALL: { //message = String
                    System.out.println("DELETE_CALL");
                    String login = objectMapper.readValue(message.getBody(), String.class);
                    CallRepository.deleteByLogin(login);
                    break;
                }
                case UPDATE_CALL: { //message = Call
                    System.out.println("UPDATE_CALL");
                    Call call = objectMapper.readValue(message.getBody(), Call.class);
                    CallRepository.updateCall(call);
                    break;
                }
                case CALL_ONE_MINUTE: { //message = Call
                    System.out.println("CALL_ONE_SECOND");
                    Call call = objectMapper.readValue(message.getBody(), Call.class);
                    Call realCall = CallRepository.findCallByLogin(call.getLogin());
                    realCall.setCall_balance(realCall.getCall_balance()-call.getCall_balance());
                    CallRepository.updateCall(realCall);
                    break;
                }
                case CALL_ONE_SECOND: { //message = Call
                    System.out.println("CALL_ONE_SECOND");
                    Call call = objectMapper.readValue(message.getBody(), Call.class);
                    Call realCall = CallRepository.findCallByLogin(call.getLogin());
                    realCall.setCall_balance(realCall.getCall_balance()-call.getCall_balance());
                    CallRepository.updateCall(realCall);
                    break;
                }
                case STOP_CALL: {
                    System.out.println("STOP_CALL");
                    break;
                }

                /** Internet */

                case CREATE_INTERNET: { //message = Internet
                    System.out.println("CREATE_INTERNET");
                    Internet internet = objectMapper.readValue(message.getBody(), Internet.class);
                    InternetRepository.save(internet);
                    break;
                }
                case DELETE_INTERNET: { //message = String
                    System.out.println("DELETE_INTERNET");
                    String login = objectMapper.readValue(message.getBody(), String.class);
                    InternetRepository.deleteByLogin(login);
                    break;
                }
                case UPDATE_INTERNET: { //message = Call
                    System.out.println("UPDATE_INTERNET");
                    Internet internet = objectMapper.readValue(message.getBody(), Internet.class);
                    InternetRepository.updateInternet(internet);
                    break;
                }
                case INTERNET_USE_KILOBYTE: { //message = Call
                    System.out.println("INTERNET_USE_KILOBYTE");
                    Internet internet = objectMapper.readValue(message.getBody(), Internet.class);
                    Internet realInternet = InternetRepository.findInternetByLogin(internet.getLogin());
                    realInternet.setInternet_balance(realInternet.getInternet_balance()-realInternet.getInternet_balance());
                    InternetRepository.updateInternet(realInternet);
                    break;
                }
                /** Sms */

                case CREATE_SMS: {
                    System.out.println("CREATE_SMS");
                    Sms sms = objectMapper.readValue(message.getBody(), Sms.class);
                    SmsRepository.save(sms);
                    break;
                }
                case DELETE_SMS: {
                    System.out.println("DELETE_SMS");
                    String login = objectMapper.readValue(message.getBody(), String.class);
                    SmsRepository.deleteByLogin(login);
                    break;
                }
                case UPDATE_SMS: {
                    System.out.println("UPDATE_SMS");
                    Sms sms = objectMapper.readValue(message.getBody(), Sms.class);
                    SmsRepository.updateSms(sms);
                    break;
                }

                default: {
                    break;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

}
