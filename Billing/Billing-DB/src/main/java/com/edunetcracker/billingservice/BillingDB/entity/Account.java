package com.edunetcracker.billingservice.BillingDB.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@SequenceGenerator(sequenceName = "account_sequence", name = "account_generator", initialValue = 1, allocationSize = 1)
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    public static final String NamedQuery_SetAccountBlock = "setAccountBlock";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_generator")
    @Nullable
    private Long number = null;

    @NotNull(message = "Block can't be null")
    @NotBlank(message = "Block can't be empty")
    private Long block = null;

    @NotNull(message = "Balance can't be null")
    @NotBlank(message = "Balance can't be empty")
    private Long balance = null;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(number, account.number);
    }

}
