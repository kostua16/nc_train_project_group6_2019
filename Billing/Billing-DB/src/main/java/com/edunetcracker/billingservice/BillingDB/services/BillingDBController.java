package com.edunetcracker.billingservice.BillingDB.services;

import com.edunetcracker.billingservice.BillingDB.entity.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

//TODO

@RestController
@RequestMapping("/api/billing-db")
@Validated
public class BillingDBController {

    @Autowired
    private IAccountRepository repository=null;

    Logger logger = LoggerFactory.getLogger(BillingDBController.class);

    @GetMapping(path = "byNumber/{NUMBER}", produces = APPLICATION_JSON_VALUE)
    @Async
    public CompletableFuture<ResponseEntity<Account>> getOneAccountByNumber(@PathVariable("number") @Valid @Min(100000) @NotNull Long number){
        final Optional<Account> byNumber = repository.findByNumber(number, PageRequest.of(0,2));

        if(logger.isDebugEnabled()){
            logger.debug("getOneAccountByNumber[number = {}, result = {}]", number, byNumber);
        }

        if(byNumber.isPresent()){
            return CompletableFuture.completedFuture(new ResponseEntity<Account>(byNumber.get(), HttpStatus.OK));
        } else {
            return CompletableFuture.completedFuture(new ResponseEntity<Account>((Account) null, HttpStatus.NOT_FOUND));
        }
    }
}
