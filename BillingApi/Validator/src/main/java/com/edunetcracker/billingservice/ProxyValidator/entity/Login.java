package com.edunetcracker.billingservice.ProxyValidator.entity;

import java.util.Objects;

public class Login {

    private String login;

    private String password;

    public Login(String login, String password) {
        this.login = login;
        this.password = password;
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        Login login = (Login) o;
        if(this.hashCode() == login.hashCode()) {
            return true;
        }
        return false;
        //return Objects.equals(login.login, login.password) && Objects.equals(login, password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password);
    }
}
