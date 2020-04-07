package com.edunetcracker.billingservice.ProxyProxy.rabbit;

public interface RabbitMQMessageType {
    public static final String GET_ACCOUNT_INFORMATION ="GET_ACCOUNT_INFORMATION";
    public static final String ERROR_MESSAGE ="ERROR_MESSAGE";
    /** Account **/
    public static final String CREATE_ACCOUNT ="CREATE_ACCOUNT";        //message = Account
    public static final String UPDATE_ACCOUNT ="UPDATE_ACCOUNT";        //message = Account
    public static final String DELETE_ACCOUNT ="DELETE_ACCOUNT";        //message = String
    /** Balance **/
    public static final String ADD_BALANCE ="ADD_BALANCE";              //message = Account
    /** Tariff **/
    public static final String CREATE_TARIFF ="CREATE_TARIFF";          //message = Tariff
    public static final String UPDATE_TARIFF ="UPDATE_TARIFF";          //message = Tariff
    public static final String DELETE_TARIFF ="DELETE_TARIFF";          //message = String
    /** Call **/
    public static final String CREATE_CALL ="CREATE_CALL";              //message = Call
    public static final String UPDATE_CALL ="UPDATE_CALL";              //message = Call
    public static final String DELETE_CALL ="DELETE_CALL";              //message = String
    public static final String CALL ="CALL";                            //message = Call
    public static final String CALL_ONE_MINUTE ="CALL_ONE_MINUTE";      //message = Call
    public static final String CALL_ONE_SECOND ="CALL_ONE_SECOND";      //message = Call
    public static final String STOP_CALL ="STOP_CALL";                  //message = Call
    /** Internet **/
    public static final String CREATE_INTERNET ="CREATE_INTERNET";                  //message = Internet
    public static final String UPDATE_INTERNET ="UPDATE_INTERNET";                  //message = Internet
    public static final String DELETE_INTERNET ="DELETE_INTERNET";                  //message = String
    public static final String INTERNET_USE_KILOBYTE ="INTERNET_USE_KILOBYTE";      //message = Internet
    /** Sms **/
    public static final String CREATE_SMS ="CREATE_SMS";                  //message = Sms
    public static final String UPDATE_SMS ="UPDATE_SMS";                  //message = Sms
    public static final String DELETE_SMS ="DELETE_SMS";                  //message = String
    public static final String REQUEST_SMS ="REQUEST_SMS";                  //message = String
}

