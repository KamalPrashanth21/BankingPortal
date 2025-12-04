package com.banking.portal.entity;

import com.banking.portal.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Data
@Table(name = "transactions")
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

//    public Transaction(TransactionType transactionType, BigDecimal amount, String description, LocalDateTime timestamp, Account account){
//        this.transactionType=transactionType;
//        this.amount=amount;
//        this.description=description;
//        this.timestamp=timestamp;
//        this.account=account;
//    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType; //Either debit or credit

    @Column(nullable = false)
    private BigDecimal amount; //financial data involves BigDecimal type

    private String description;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;
}
