package com.edunetcracker.billingservice.ProxyValidator.checks_and_helpers;

import com.edunetcracker.billingservice.ProxyValidator.entity.Account;
import com.edunetcracker.billingservice.ProxyValidator.session.SessionService;
import com.edunetcracker.billingservice.ProxyValidator.web.CRM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class Checks {

    @Autowired
    Helpers helpers;

    @Autowired
    SessionService sessionService;
    public static final String USER ="USER";
    public static final String ADMINISTRATOR ="ADMINISTRATOR";
    List<String> ranges = Arrays.asList(USER,ADMINISTRATOR);

    Logger LOG = LoggerFactory.getLogger(Checks.class);

    /*************checks**************/
    public Boolean isAccountExists(String accountLogin) {
        try {
            String url = helpers.getUrlBilling() + "/getAccountByLogin/?login=" + accountLogin;
            Account account = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Account.class).getBody();
            LOG.info("isAccountExists.account={}", account);
            if (account == null)
                return false;

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean checkByToken(String token) {
        final Boolean result = sessionService.inSession(token);
        LOG.info("checkByToken.token[{}]=>{}", token, result);
        return result;
    }
    public String getLoginByTokenAndCheck(String token) {
        if (checkByToken(token)) {
            String login = sessionService.getLogin(token).getLogin();
            LOG.info("getLoginByTokenAndCheck.token[{}]=>login[{}]", token, login);
            if (isAccountExists(login)) {
                LOG.info("getLoginByTokenAndCheck.OK");
                return login;
            }
        }
        return null;
    }

    public boolean isAvailableInRanges(String mainRang){
        for(int a = 0; a< ranges.size(); a++){
            if(mainRang.equals(ranges.get(a)))
                return true;
        }
        return false;
    }

}
