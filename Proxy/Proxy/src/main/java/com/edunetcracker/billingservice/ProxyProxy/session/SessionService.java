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

    public String newSession(Login login){

        // если сессии для пользователя нет
        if(!loginMap.containsKey(login)) {
            String session = generateSession();
            loginMap.put(login, session);
            sessionMap.put(session, login);
            return session;
        }

        return null;
    }

    public String getSession(Login login){
        return loginMap.get(login);
    }

    public Login getLogin(String session){
        return sessionMap.get(session);
    }

    public void deleteSession(String session){
        if(sessionMap.containsKey(session)) {
            Login login = sessionMap.get(session);
            sessionMap.remove(session);
            loginMap.remove(login);
        }
    }

    private String generateSession(){
        //return UUID.randomUUID().toString() + UUID.randomUUID().toString();
        return UUID.randomUUID().toString();
    }
}
