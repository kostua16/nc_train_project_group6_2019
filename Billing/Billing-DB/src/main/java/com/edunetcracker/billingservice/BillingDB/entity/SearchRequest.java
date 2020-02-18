package com.edunetcracker.billingservice.BillingDB.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchRequest {

    @NotNull
    private String query = "";
    @NotNull
    private String type= "";
    @NotNull
    private String criteria= "";
    @NotNull
    private Integer page= 0;
    @NotNull
    private Integer size= 10;
    @NotNull
    private String sortField = "";
    @NotNull
    private Integer sortDirection = 0;

    public String getType(String defValue) {
        return isTypeEmpty() ? defValue : this.type;
    }
    public String getCriteria(String defValue) {
        return isCriteriaEmpty() ? defValue : this.criteria;
    }
    public String getQuery(String defValue) {
        return isQueryEmpty() ? defValue : this.query;
    }
    public String getSortField(String defValue) {
        return isSortFieldEmpty() ? defValue : this.sortField;
    }


    public SearchRequest() {
    }

    public Map<String, String> toMap(){
        Map<String, String> map = new HashMap<>();
        if(!isEmpty(this.query)) map.put("query", this.query);
        if(!isEmpty(this.type)) map.put("type", this.type);
        if(!isEmpty(this.criteria)) map.put("criteria", this.criteria);
        if(!isEmpty(this.sortField)) map.put("sortField", this.sortField);
        if(this.page!=null) map.put("page", this.page.toString());
        if(this.size!=null) map.put("size", this.size.toString());
        if(this.sortDirection!=null) map.put("sortDirection", this.sortDirection.toString());

        return map;
    }

    public String asRequestParameters(String serviceUrl) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(serviceUrl);

        if(!isEmpty(this.query)) builder.queryParam("query", this.query);
        if(!isEmpty(this.type)) builder.queryParam("type", this.type);
        if(!isEmpty(this.criteria)) builder.queryParam("criteria", this.criteria);
        if(!isEmpty(this.sortField)) builder.queryParam("sortField", this.sortField);
        if(this.page!=null) builder.queryParam("page", this.page.toString());
        if(this.size!=null) builder.queryParam("size", this.size.toString());
        if(this.sortDirection!=null) builder.queryParam("sortDirection", this.sortDirection.toString());

        return builder.toUriString();
    }

    public SearchRequest(@NotNull String query, @NotNull String type, @NotNull String criteria, @NotNull Integer page, @NotNull Integer size, @NotNull String sortField, @NotNull Integer sortDirection) {
        this.query = query;
        this.type = type;
        this.criteria = criteria;
        this.page = page;
        this.size = size;
        this.sortField = sortField;
        this.sortDirection = sortDirection;
    }

    public SearchRequest(@NotNull String query, @NotNull String type, @NotNull String criteria, @NotNull Integer page, @NotNull Integer size, @NotNull String sortField) {
        this.query = query;
        this.type = type;
        this.criteria = criteria;
        this.page = page;
        this.size = size;
        this.sortField = sortField;
    }

    public SearchRequest(@NotNull String query, @NotNull String type, @NotNull String criteria, @NotNull Integer page, @NotNull Integer size) {
        this.query = query;
        this.type = type;
        this.criteria = criteria;
        this.page = page;
        this.size = size;
    }

    public SearchRequest(@NotNull String query, @NotNull String type, @NotNull String criteria) {
        this.query = query;
        this.type = type;
        this.criteria = criteria;
    }

    protected Sort getSort(String defaultSortField) {
        Sort result;
        if(isSortFieldEmpty()){
            if(isEmpty(defaultSortField)){
                return Sort.unsorted();
            } else {
                result = Sort.by(defaultSortField);
            }
        } else {
            result = Sort.by(this.sortField);
        }
        if(sortDirection>0){
            return result.ascending();
        } else if(sortDirection<0) {
            return result.descending();
        } else {
            return result;
        }
    }

    public Pageable getPageParameters(String defaultSortField){
        int pageNumber = this.page == null ? 0 : this.page;
        int pageSize = this.size == null ? 10 : this.size > 50 ? 50 : this.size;
        Sort sort = getSort(defaultSortField);
        return PageRequest.of(pageNumber, pageSize, sort);
    }

    protected Boolean isEmpty(String value){
        return value == null || "".equals(value) || " ".equals(value);
    }

    public Boolean isQueryEmpty(){
        return isEmpty(this.query);
    }
    public Boolean isTypeEmpty(){
        return isEmpty(this.type);
    }
    public Boolean isCriteriaEmpty(){
        return isEmpty(this.criteria);
    }

    public Boolean isSortFieldEmpty(){
        return isEmpty(this.sortField);
    }

}
