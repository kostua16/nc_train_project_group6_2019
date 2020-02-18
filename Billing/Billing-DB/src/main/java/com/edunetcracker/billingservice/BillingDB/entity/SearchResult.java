package com.edunetcracker.billingservice.BillingDB.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResult<T> {

    private List<T> results = Collections.emptyList();
    private Long countResults = 0L;
    private Integer countPages = 0;
    private Integer page = 0;
    private Integer size = 0;

    public SearchResult() {

    }

    public SearchResult<T> from(@NotNull Page<T> page){
        this.setResults(page.getContent());
        this.setCountResults(page.getTotalElements());
        this.setCountPages(page.getTotalPages());
        this.setPage(page.getNumber());
        this.setSize(page.getSize());
        return this;
    }
    public SearchResult<T> from(@NotNull List<T> list){
        this.setResults(list);
        this.setCountResults((long) list.size());
        this.setCountPages(1);
        this.setPage(0);
        this.setSize(list.size());
        return this;
    }
    public SearchResult<T> from(@NotNull T singleResult){
        this.setResults(Collections.singletonList(singleResult));
        this.setCountResults(1L);
        this.setCountPages(1);
        this.setPage(0);
        this.setSize(1);
        return this;
    }
    public SearchResult<T> from(@NotNull Optional<T> singleResult){
        if(singleResult.isPresent()){
            return this.from(singleResult.get());
        } else {
            return empty();
        }
    }
    public SearchResult<T> empty() {
        this.setResults(Collections.emptyList());
        this.setCountResults(0L);
        this.setCountPages(0);
        this.setPage(0);
        this.setSize(0);
        return this;
    }
    public SearchResult<T> setPageDetail(Pageable pageDetail) {
        this.setPage(pageDetail.getPageNumber());
        this.setSize(pageDetail.getPageSize());
        return this;
    }

    public static  <T, S extends SearchResult<T>> ResponseEntity<S> responseEntity(S searchResult){
        if(searchResult.getCountResults()<1){
            return new ResponseEntity<S>(searchResult, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<S>(searchResult, HttpStatus.OK);
        }
    }

}
