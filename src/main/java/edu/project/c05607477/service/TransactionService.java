package edu.project.c05607477.service;

import edu.project.c05607477.component.TransactionNumberGenerator;
import edu.project.c05607477.exceptions.InvalidTransactionRequestException;
import edu.project.c05607477.jpa.entity.Account;
import edu.project.c05607477.jpa.entity.Transaction;
import edu.project.c05607477.jpa.entity.TxStatus;
import edu.project.c05607477.jpa.entity.TxType;
import edu.project.c05607477.jpa.repository.AccountRepository;
import edu.project.c05607477.jpa.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;

@Service
@Transactional
public class TransactionService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionNumberGenerator transactionNumberGenerator;

    public static class TransactionResult {

        private String txNo;
        private TxStatus txStatus;
        private BigInteger fromAccountBalance;
        private BigInteger toAccountBalance;
        private Date timestamp;

        public TransactionResult(String txNo, TxStatus txStatus, Date timestamp) {
            this.txNo = txNo;
            this.txStatus = txStatus;
            this.timestamp = timestamp;
        }

        public BigInteger getFromAccountBalance() {
            return fromAccountBalance;
        }

        public void setFromAccountBalance(BigInteger fromAccountBalance) {
            this.fromAccountBalance = fromAccountBalance;
        }

        public BigInteger getToAccountBalance() {
            return toAccountBalance;
        }

        public void setToAccountBalance(BigInteger toAccountBalance) {
            this.toAccountBalance = toAccountBalance;
        }

        public String getTxNo() {
            return txNo;
        }

        public TxStatus getTxStatus() {
            return txStatus;
        }

        public Date getTimestamp() {
            return timestamp;
        }
    }

    public TransactionResult deposit(Account account, BigInteger amount) {
        if (BigInteger.ZERO.compareTo(amount) >= 0) {
            throw new InvalidTransactionRequestException("The amount is too low");
        }

        BigInteger beforeBalance = account.getBalance();
        account.setBalance(beforeBalance.add(amount));
        BigInteger afterBalance = account.getBalance();
        accountRepository.save(account);

        String txNo = transactionNumberGenerator.generateTransactionNumber();
        TxStatus txStatus = TxStatus.Success;
        Date timestamp = new Date();
        TransactionResult result = new TransactionResult(txNo, txStatus, timestamp);
        result.setToAccountBalance(account.getBalance());

        Transaction txForDeposit = new Transaction();
        txForDeposit.setTxNo(txNo);
        txForDeposit.setTargetAccount(account);
        txForDeposit.setTxType(TxType.Deposit);
        txForDeposit.setTxStatus(txStatus);
        txForDeposit.setBeforeBalance(beforeBalance);
        txForDeposit.setAfterBalance(afterBalance);
        txForDeposit.setAmount(amount);
        txForDeposit.setTimestamp(timestamp);

        transactionRepository.save(txForDeposit);

        return result;
    }

    public TransactionResult withdrawal(Account account, BigInteger amount) {
        if (BigInteger.ZERO.compareTo(amount) >= 0) {
            throw new InvalidTransactionRequestException("The amount is too low");
        }

        if (!isSufficientBalance(account, amount)) {
            throw new InvalidTransactionRequestException("Insufficient balance");
        }

        BigInteger beforeBalance = account.getBalance();
        account.setBalance(beforeBalance.add(amount));
        BigInteger afterBalance = account.getBalance();
        accountRepository.save(account);

        String txNo = transactionNumberGenerator.generateTransactionNumber();
        TxStatus txStatus = TxStatus.Success;
        Date timestamp = new Date();
        TransactionResult result = new TransactionResult(txNo, txStatus, timestamp);
        result.setFromAccountBalance(account.getBalance());

        Transaction txForWithdrawal = new Transaction();
        txForWithdrawal.setTxNo(txNo);
        txForWithdrawal.setTargetAccount(account);
        txForWithdrawal.setTxType(TxType.Withdrawal);
        txForWithdrawal.setTxStatus(txStatus);
        txForWithdrawal.setBeforeBalance(beforeBalance);
        txForWithdrawal.setAfterBalance(afterBalance);
        txForWithdrawal.setAmount(amount);
        txForWithdrawal.setTimestamp(timestamp);

        transactionRepository.save(txForWithdrawal);

        return result;
    }

    public TransactionResult transferTo(
            Account sender, Account recipient, BigInteger amount
    ) {
        if (sender.getAccountId() == recipient.getAccountId()) {
            throw new InvalidTransactionRequestException("not allowed between the same account");
        }

        if (BigInteger.ZERO.compareTo(amount) >= 0) {
            throw new InvalidTransactionRequestException("The amount is too low");
        }

        if (!isSufficientBalance(sender, amount)) {
            throw new InvalidTransactionRequestException("Insufficient balance");
        }

        BigInteger beforeSenderBalance = sender.getBalance();
        sender.setBalance(beforeSenderBalance.subtract(amount));
        BigInteger afterSenderBalance = sender.getBalance();

        BigInteger beforeRecipientBalance = recipient.getBalance();
        recipient.setBalance(beforeRecipientBalance.add(amount));
        BigInteger afterRecipientBalance = recipient.getBalance();

        accountRepository.save(Arrays.asList(sender, recipient));

        String txNo = transactionNumberGenerator.generateTransactionNumber();
        TxStatus txStatus = TxStatus.Success;
        Date timestamp = new Date();
        TransactionResult result = new TransactionResult(txNo, txStatus, timestamp);
        result.setFromAccountBalance(sender.getBalance());
        result.setToAccountBalance(recipient.getBalance());

        Transaction txForReceiving = new Transaction();
        txForReceiving.setTxNo(txNo);
        txForReceiving.setSourceAccount(sender);
        txForReceiving.setTargetAccount(recipient);
        txForReceiving.setTxType(TxType.Receiving);
        txForReceiving.setTxStatus(txStatus);
        txForReceiving.setBeforeBalance(beforeRecipientBalance);
        txForReceiving.setAfterBalance(afterRecipientBalance);
        txForReceiving.setAmount(amount);
        txForReceiving.setTimestamp(timestamp);

        Transaction txForSending = new Transaction();
        txForSending.setTxNo(txNo);
        txForSending.setSourceAccount(recipient);
        txForSending.setTargetAccount(sender);
        txForSending.setTxType(TxType.Sending);
        txForSending.setTxStatus(txStatus);
        txForSending.setBeforeBalance(beforeSenderBalance);
        txForSending.setAfterBalance(afterSenderBalance);
        txForSending.setAmount(amount);
        txForSending.setTimestamp(timestamp);

        transactionRepository.save(Arrays.asList(txForReceiving, txForSending));

        return result;
    }

    private boolean isSufficientBalance(Account account, BigInteger amount) {
        BigInteger balance = account.getBalance();
        return balance.compareTo(amount) >= 0;
    }
}
