package com.edunetcracker.billingservice.ProxyValidator.validator;

import com.edunetcracker.billingservice.ProxyValidator.checks_and_helpers.Checks;
import com.edunetcracker.billingservice.ProxyValidator.checks_and_helpers.Helpers;
import com.edunetcracker.billingservice.ProxyValidator.entity.Account;
import com.edunetcracker.billingservice.ProxyValidator.entity.Login;
import com.edunetcracker.billingservice.ProxyValidator.session.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
/*@Service*/
public class LoginController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private Helpers helpers;

    @Autowired
    private Checks checks;

    //  return session "id"
    @GetMapping("login")
    public ResponseEntity<String> login(@RequestParam("login") String login,
                                        @RequestParam("password") String password) {
        try {
            System.out.println("1/");
            if (checks.isAccountExists(login)) {//если есть аккаунт
                System.out.println("2/");
                Login login1 = new Login(login, password);
                Login login2 = getAccountLoginAndPassword(login);

                if (login2 != null && login1.getLogin().equals(login2.getLogin()) && login1.getPassword().equals(login2.getPassword())) {//если правильный пароль

                    System.out.println("login/");
                    String session = sessionService.newSession(new Login(login, password));
                    System.out.println("/login");
                    return new ResponseEntity<>(session, HttpStatus.OK);

                } else return new ResponseEntity<>((String) null, HttpStatus.NOT_FOUND);
            } else return new ResponseEntity<>((String) null, HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>((String) null, HttpStatus.INTERNAL_SERVER_ERROR);  //500
        }
    }

    @GetMapping("logout")
    public ResponseEntity<Boolean> logout(@RequestParam("token") String token) {
        try {
            if (sessionService.getLogin(token) != null) {
                sessionService.deleteSession(token);
                return new ResponseEntity<>(true, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
    }


    public Login getAccountLoginAndPassword(@RequestParam("login") String login) {
        try {
            //TODO
            String url = helpers.getUrlBilling() + "/getAccountByLogin/?login=" + login;
            ResponseEntity<Account> responseAccount = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Account.class);
            Login login1 = new Login(responseAccount.getBody().getLogin(), responseAccount.getBody().getPassword());
            return login1;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }
}
