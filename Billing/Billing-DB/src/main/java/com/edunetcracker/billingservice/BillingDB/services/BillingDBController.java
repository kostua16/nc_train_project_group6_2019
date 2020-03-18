package com.edunetcracker.billingservice.BillingDB.services;

import com.edunetcracker.billingservice.BillingDB.entity.Account;
import com.edunetcracker.billingservice.BillingDB.entity.AccountSearchResult;
import com.edunetcracker.billingservice.BillingDB.entity.SearchRequest;
import com.edunetcracker.billingservice.BillingDB.entity.SearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Async
    public CompletableFuture<ResponseEntity<AccountSearchResult>> searchAccount(@Valid SearchRequest request ){
        AccountSearchResult result = new AccountSearchResult();


        String searchType = request.getType("number");
        String searchCriteria = request.getCriteria("contains");
        String searchQuery = request.getQuery();
        Pageable searchParameters = request.getPageParameters(searchType);
        result.setPageDetail(searchParameters);

        /*
        if("name".equals(searchType)){
            if(request.isQueryEmpty()){
                result.from(repository.findByNameIsNotNull(searchParameters));
            } else {
                if ("contains".equals(searchCriteria)) {
                    result.from(repository.findByNameContaining(searchQuery, searchParameters));
                } else if ("equals".equals(searchCriteria)) {
                    result.from(repository.findByName(searchQuery, searchParameters));
                } else if ("not_contains".equals(searchCriteria)) {
                    result.from(repository.findByNameNotContaining(searchQuery, searchParameters));
                }
            }

        }

        if("number".equals(searchType)){
            if(request.isQueryEmpty()){
                result.from(repository.findByNumberIsNotNull(searchParameters));
            } else {
                if("contains".equals(searchCriteria)){
                    result.from(repository.findByNumberContaining(searchQuery, searchParameters));
                } else if ("equals".equals(searchCriteria)){
                    result.from(repository.findByNumber(searchQuery, searchParameters));
                }
                else if ("not_contains".equals(searchCriteria)){
                    result.from(repository.findByIsbnNotContaining(searchQuery, searchParameters));
                }
            }

        }

         */

        if("number".equals(searchType)){
            if(request.isQueryEmpty()){
                result.from(repository.findAll(searchParameters));
            } else {
                Optional<Account> foundAccount;
                try {
                    foundAccount = repository.findByNumber(Long.parseLong(searchQuery));
                } catch (NumberFormatException e){
                    foundAccount = Optional.empty();
                }
                result.from(foundAccount);
            }
        }

        if(logger.isDebugEnabled()){
            logger.debug("searchAccount[request = {}, result = {}]", request, result);
        }

        return CompletableFuture.completedFuture(SearchResult.responseEntity(result));
    }
}
