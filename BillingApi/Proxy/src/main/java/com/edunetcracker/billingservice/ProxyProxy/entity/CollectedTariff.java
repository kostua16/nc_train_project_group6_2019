package com.edunetcracker.billingservice.ProxyProxy.entity;

public class CollectedTariff {

    private String name;
    private TariffCall tariffCall;
    private TariffInternet tariffInternet;
    private TariffSms tariffSms;

    /*************************************/

    public CollectedTariff(String name, TariffCall tariffCall, TariffInternet tariffInternet, TariffSms tariffSms) {
        this.name = name;
        this.tariffCall = tariffCall;
        this.tariffInternet = tariffInternet;
        this.tariffSms = tariffSms;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TariffCall getTariffCall() {
        return tariffCall;
    }

    public void setTariffCall(TariffCall tariffCall) {
        this.tariffCall = tariffCall;
    }

    public TariffInternet getTariffInternet() {
        return tariffInternet;
    }

    public void setTariffInternet(TariffInternet tariffInternet) {
        this.tariffInternet = tariffInternet;
    }

    public TariffSms getTariffSms() {
        return tariffSms;
    }

    public void setTariffSms(TariffSms tariffSms) {
        this.tariffSms = tariffSms;
    }

}
