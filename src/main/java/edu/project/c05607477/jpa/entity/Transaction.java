package edu.project.c05607477.jpa.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = "transaction")
public class Transaction implements Serializable {

    @Id
    @GeneratedValue
    private Long txId;

    @Column
    private String txNo;

    @JoinColumn(name = "source_account_id")
    @OneToOne
    private Account sourceAccount;

    @JoinColumn(name = "target_account_id")
    @OneToOne
    private Account targetAccount;

    @Column
    private BigInteger amount;

    @Column
    private BigInteger beforeBalance;

    @Column
    private BigInteger afterBalance;

    @Enumerated(EnumType.STRING)
    @Column
    private TxType txType;

    @Enumerated(EnumType.STRING)
    @Column
    private TxStatus txStatus;

    @Column
    private String reason;

    @Column
    private Date timestamp;

    public Long getTxId() {
        return txId;
    }

    public void setTxId(Long txId) {
        this.txId = txId;
    }

    public String getTxNo() {
        return txNo;
    }

    public void setTxNo(String txNo) {
        this.txNo = txNo;
    }

    public Account getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(Account fromAccount) {
        this.sourceAccount = fromAccount;
    }

    public Account getTargetAccount() {
        return targetAccount;
    }

    public void setTargetAccount(Account toAccount) {
        this.targetAccount = toAccount;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }

    public BigInteger getBeforeBalance() {
        return beforeBalance;
    }

    public void setBeforeBalance(BigInteger beforeBalance) {
        this.beforeBalance = beforeBalance;
    }

    public BigInteger getAfterBalance() {
        return afterBalance;
    }

    public void setAfterBalance(BigInteger afterBalance) {
        this.afterBalance = afterBalance;
    }

    public TxType getTxType() {
        return txType;
    }

    public void setTxType(TxType txType) {
        this.txType = txType;
    }

    public TxStatus getTxStatus() {
        return txStatus;
    }

    public void setTxStatus(TxStatus txStatus) {
        this.txStatus = txStatus;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
