package com.edunetcracker.billingservice.ProxyProxy.proxy;

import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Checks;
import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Helpers;
import com.edunetcracker.billingservice.ProxyProxy.entity.*;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQMessageType;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/*@RestController*/
@Service
public class TariffController {

    @Autowired
    private RabbitMQSender rabbitMQSender;


    @Autowired
    private Checks checks;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    OperationsService operationsService;

    Logger LOG = LoggerFactory.getLogger(TariffController.class);

    /////////////////////////////////////////////////////////////////////////////////////////////

    public Boolean isTariffExists(String tariffName) {
        return operationsService.request("/getTariffByName/?name=" + tariffName, HttpMethod.GET, Tariff.class) != null;
    }

    public Boolean createTariff(CollectedTariff tariff) throws JsonProcessingException {
        if(tariff!=null && !checks.isTariffExists(tariff.getName())){
            final Tariff baseTariff = new Tariff();
            baseTariff.setName(tariff.getName());
            rabbitMQSender.send(baseTariff, RabbitMQMessageType.CREATE_TARIFF);
            rabbitMQSender.send(tariff.getTariffCall(), RabbitMQMessageType.CREATE_TARIFF_CALL);
            rabbitMQSender.send(tariff.getTariffInternet(), RabbitMQMessageType.CREATE_TARIFF_INTERNET);
            rabbitMQSender.send(tariff.getTariffSms(), RabbitMQMessageType.CREATE_TARIFF_SMS);
            return true;
        }
        return false;
    }

    public CollectedTariff getTariff(String tariffName) {
        if(isTariffExists(tariffName)){
            try {
                final TariffCall tariffCall = operationsService.request("/getTariffCallByName/?name=" + tariffName, HttpMethod.GET, TariffCall.class);
                final TariffInternet tariffInternet = operationsService.request("/getTariffInternetByName/?name=" + tariffName, HttpMethod.GET, TariffInternet.class);
                final TariffSms tariffSms = operationsService.request("/getTariffSmsByName/?name=" + tariffName, HttpMethod.GET, TariffSms.class);
                return new CollectedTariff(tariffName, tariffCall, tariffInternet, tariffSms);
            } catch (Exception e) {
                LOG.error("getTariff failed", e);
            }
        }
        return null;
    }

    public List<Tariff> getAllBaseTariffs() {
        return operationsService.requestList("/getAllTariff", HttpMethod.GET, Tariff.class);
    }

    public List<CollectedTariff> getAllCollectedTariff() {
        List<CollectedTariff> collectedTariffs = new ArrayList<>();
        try {
            List<Tariff> tariffs = getAllBaseTariffs();
            for (Tariff tariff : tariffs) {
                collectedTariffs.add(getTariff(tariff.getName()));
            }

        } catch (Exception e) {
            LOG.error("getAllCollectedTariff failed", e);
        }
        return collectedTariffs;
    }

    public List<Map> getAllCollectedTariffAsMapList(){
        List<Map> returnT = new ArrayList<>();

        List<CollectedTariff> tariffs = getAllCollectedTariff();
        for (CollectedTariff current : tariffs) {
            Map<String, String> tariff = new HashMap<>();
            tariff.put("name", current.getName());
            tariff.put("call", current.getTariffCall().getCall_balance().toString());
            tariff.put("internet", current.getTariffInternet().getInternet_balance().toString());
            tariff.put("sms", current.getTariffSms().getSms_balance().toString());
            returnT.add(tariff);
        }
        return returnT;
    }

    public Boolean updateTariff(Tariff newTariff) {
        if(isTariffExists(newTariff.getName())){
            try {
                rabbitMQSender.send(newTariff, RabbitMQMessageType.UPDATE_TARIFF);
                return true;
            } catch (Exception e) {
                LOG.error("updateTariff failed", e);

            }
        }
        return false;
    }

    public Boolean deleteTariff(String name) {
        if(isTariffExists(name)){
            try {
                rabbitMQSender.send(name, RabbitMQMessageType.DELETE_TARIFF);
                rabbitMQSender.send(name, RabbitMQMessageType.DELETE_TARIFF_CALL);
                rabbitMQSender.send(name, RabbitMQMessageType.DELETE_TARIFF_INTERNET);
                rabbitMQSender.send(name, RabbitMQMessageType.DELETE_TARIFF_SMS);
                return true;
            } catch (Exception e) {
                LOG.error("deleteTariff failed", e);
            }
        }
        return false;

    }

}
