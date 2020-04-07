package com.edunetcracker.billingservice.ProxyProxy.session;

import com.edunetcracker.billingservice.ProxyProxy.entity.Login;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class SessionService {

    private Map<Login, String> loginMap = new HashMap<>(); //{String login, String password}, String session
    private Map<String, Login> sessionMap = new HashMap<>(); //String session, {String login, String password}

    public String newSession(Login login) {

        String session = generateSession();
        // если сессии для пользователя нет
        if (!loginMap.containsKey(login)) {
            System.out.println("---no session---");
            loginMap.put(login, session);
            sessionMap.put(session, login);
            return session;
        }
        System.out.println("___oldSession___");
        System.out.println("---loginMap");
        int i = 0;
        for (Map.Entry<Login, String> e : loginMap.entrySet()) {
            System.out.println(i + " " + e.getKey().getLogin() + " -> " + e.getValue());
            ++i; //iterate
        }
        System.out.println("---sessionMap");
        i = 0;
        for (Map.Entry<String, Login> e : sessionMap.entrySet()) {
            System.out.println(i + " " + e.getKey() + " -> " + e.getValue().getLogin());
            ++i; //iterate
        }
        System.out.println("!!!oldSession!!!");
        sessionMap.remove(loginMap.get(login));
        loginMap.put(login, session);
        sessionMap.put(session, login);

        System.out.println("---newSession---");
        System.out.println("---loginMap");
        i = 0;
        for (Map.Entry<Login, String> e : loginMap.entrySet()) {
            System.out.println(i + " " + e.getKey().getLogin() + " -> " + e.getValue());
            ++i; //iterate
        }
        System.out.println("---sessionMap");
        i = 0;
        for (Map.Entry<String, Login> e : sessionMap.entrySet()) {
            System.out.println(i + " " + e.getKey() + " -> " + e.getValue().getLogin());
            ++i; //iterate
        }
        System.out.println("!!!newSession!!!");
        return session;
    }

    public String getSession(Login login) {
        if (loginMap.containsKey(login)) {
            return loginMap.get(login);
        }
        return null;

    }

    public Login getLogin(String session) {
        if (sessionMap.containsKey(session)) {
            return sessionMap.get(session);
        }
        return null;
    }

    public void deleteSession(String session) {
        if (sessionMap.containsKey(session)) {
            Login login = sessionMap.get(session);
            sessionMap.remove(session);
            loginMap.remove(login);
        }
    }

    public Boolean inSession(String session) {
        return sessionMap.containsKey(session);
    }

    private String generateSession() {
        //return UUID.randomUUID().toString() + UUID.randomUUID().toString();
        return UUID.randomUUID().toString();
    }
}
