package edu.project.c05607477.service;

import edu.project.c05607477.component.TransactionNumberGenerator;
import edu.project.c05607477.exceptions.InvalidTransferRequestException;
import edu.project.c05607477.jpa.entity.Account;
import edu.project.c05607477.jpa.entity.Transaction;
import edu.project.c05607477.jpa.entity.TxStatus;
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

    public static class TransferToResult {

        private String txNo;
        private TxStatus txStatus;
        private Date timestamp;

        public TransferToResult(String txNo, TxStatus txStatus, Date timestamp) {
            this.txNo = txNo;
            this.txStatus = txStatus;
            this.timestamp = timestamp;
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

    public TransferToResult transferTo(
            Account sender, Account recipient, BigInteger amount
    ) {
        if (sender.getAccountId() == recipient.getAccountId()) {
            throw new InvalidTransferRequestException("not allowed between the same account");
        }

        if (BigInteger.ZERO.compareTo(amount) <= 0) {
            throw new InvalidTransferRequestException("The amount is too low");
        }

        if (!isSufficientBalance(sender, amount)) {
            throw new InvalidTransferRequestException("Insufficient balance");
        }

        sender.setBalance(sender.getBalance().subtract(amount));
        recipient.setBalance(recipient.getBalance().add(amount));

        accountRepository.save(Arrays.asList(sender, recipient));

        String txNo = transactionNumberGenerator.generateTransactionNumber();
        TxStatus txStatus = TxStatus.Success;
        Date timestamp = new Date();
        TransferToResult result = new TransferToResult(txNo, txStatus, timestamp);

        Transaction txForSender = new Transaction();
        txForSender.setTxNo(txNo);
        txForSender.setFromAccount(sender);
        txForSender.setToAccount(recipient);
        txForSender.setTxStatus(txStatus);
        txForSender.setAmount(amount);
        txForSender.setTimestamp(timestamp);

        Transaction txForRecipient = new Transaction();
        txForRecipient.setTxNo(txNo);
        txForRecipient.setFromAccount(recipient);
        txForRecipient.setToAccount(sender);
        txForRecipient.setTxStatus(txStatus);
        txForRecipient.setAmount(amount.negate());
        txForRecipient.setTimestamp(timestamp);

        transactionRepository.save(Arrays.asList(txForSender, txForRecipient));

        return result;
    }

    private boolean isSufficientBalance(Account account, BigInteger amount) {
        BigInteger balance = account.getBalance();
        return balance.compareTo(amount) >= 0;
    }
}
