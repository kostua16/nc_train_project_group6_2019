package com.edunetcracker.billingservice.BillingDB.rabbit;

import com.edunetcracker.billingservice.BillingDB.entity.Account;
import com.edunetcracker.billingservice.BillingDB.services.IAccountRepository;
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
    ObjectMapper objectMapper;

    @RabbitListener(queues = "q2")
    public void processQueue2(Message message) {
        System.out.println("COUT 1 "+message);
        System.out.println("COUT 2 "+message.toString());
        System.out.println("COUT 3 "+message.getBody());
        System.out.println("COUT 4 "+message.getMessageProperties().getMessageId());
        System.out.println("COUT 5 "+message.getMessageProperties().getType());

        try {
            //String messType = message.getMessageProperties().getType();

            switch (message.getMessageProperties().getType()) {

                case CREATE_ACCOUNT: {
                    System.out.println("IN CREATE_ACCOUNT");
                    Account account = objectMapper.readValue(message.getBody(), Account.class);
                    System.out.println("Account's name = " + account.getLogin());
                    AccountRepository.save(account);
                    break;
                }
                case DELETE_ACCOUNT: {
                    System.out.println("IN DELETE_ACCOUNT");
                    String accountLogin = objectMapper.readValue(message.getBody(), String.class);
                    System.out.println(accountLogin);
                    AccountRepository.deleteByLogin(accountLogin);
                    break;
                }
                case UPDATE_ACCOUNT: {
                    System.out.println("IN UPDATE_ACCOUNT");
                    Account account = objectMapper.readValue(message.getBody(), Account.class);
                    AccountRepository.updateAccount(account);
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
