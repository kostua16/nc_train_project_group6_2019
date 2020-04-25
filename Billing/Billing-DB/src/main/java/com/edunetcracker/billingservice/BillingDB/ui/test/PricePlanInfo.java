package com.edunetcracker.billingservice.BillingDB.ui.test;

import com.edunetcracker.billingservice.BillingDB.entity.Tariff;
import com.edunetcracker.billingservice.BillingDB.entity.TariffCall;
import com.edunetcracker.billingservice.BillingDB.entity.TariffInternet;
import com.edunetcracker.billingservice.BillingDB.entity.TariffSms;

public class PricePlanInfo {
    private Tariff tariff;
    private TariffCall tariffCall;
    private TariffSms tariffSms;
    private TariffInternet tariffInternet;

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    public TariffCall getTariffCall() {
        return tariffCall;
    }

    public void setTariffCall(TariffCall tariffCall) {
        this.tariffCall = tariffCall;
    }

    public TariffSms getTariffSms() {
        return tariffSms;
    }

    public void setTariffSms(TariffSms tariffSms) {
        this.tariffSms = tariffSms;
    }

    public TariffInternet getTariffInternet() {
        return tariffInternet;
    }

    public void setTariffInternet(TariffInternet tariffInternet) {
        this.tariffInternet = tariffInternet;
    }
}
