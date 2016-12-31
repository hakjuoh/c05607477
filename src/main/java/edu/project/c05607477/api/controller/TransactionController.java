package edu.project.c05607477.api.controller;

import edu.project.c05607477.api.model.TransferToRequest;
import edu.project.c05607477.api.model.TransferToResponse;
import edu.project.c05607477.jpa.entity.Account;
import edu.project.c05607477.service.AccountService;
import edu.project.c05607477.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@RequestMapping(path = "/api/v1/transactions")
public class TransactionController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @RequestMapping(path = "/transferTo", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public TransferToResponse transferTo(@RequestBody TransferToRequest request) {

        Long senderAccountId = request.getSenderAccountId();
        Long recipientAccountId = request.getRecipientAccountId();

        Account sender = accountService.getAccount(senderAccountId);
        Account recipient = accountService.getAccount(recipientAccountId);

        BigInteger amount = request.getAmount();

        TransactionService.TransactionResult result = transactionService.transferTo(sender, recipient, amount);

        TransferToResponse response = new TransferToResponse();
        response.setTxNo(result.getTxNo());
        response.setTxStatus(result.getTxStatus());
        return response;
    }
}
