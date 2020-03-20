package com.edunetcracker.billingservice.ProxyProxy.rabbit;

public interface RabbitMQMessageType {
    public static final String GET_ACCOUNT_INFORMATION ="GET_ACCOUNT_INFORMATION";
    public static final String ERROR_MESSAGE ="ERROR_MESSAGE";

    public static final String CREATE_ACCOUNT ="CREATE_ACCOUNT";
    public static final String UPDATE_ACCOUNT ="UPDATE_ACCOUNT";
    public static final String DELETE_ACCOUNT ="DELETE_ACCOUNT";
    public static final String ADD_BALANCE ="ADD_BALANCE";
    public static final String CALL ="CALL";
    public static final String STOP_CALL ="STOP_CALL";
}
