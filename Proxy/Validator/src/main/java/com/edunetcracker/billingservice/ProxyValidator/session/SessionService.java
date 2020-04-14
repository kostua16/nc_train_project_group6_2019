package com.edunetcracker.billingservice.ProxyValidator.session;

import com.edunetcracker.billingservice.ProxyValidator.entity.Login;
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
            loginMap.put(login, session);
            sessionMap.put(session, login);
            return session;
        }
        /*int i = 0;
        for (Map.Entry<Login, String> e : loginMap.entrySet()) {
            System.out.println(i + " " + e.getKey().getLogin() + " -> " + e.getValue());
            ++i; //iterate
        }*/

        sessionMap.remove(loginMap.get(login));
        loginMap.put(login, session);
        sessionMap.put(session, login);
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
