package edu.project.c05607477.api.controller;

import edu.project.c05607477.api.model.TransferToRequest;
import edu.project.c05607477.api.model.TransferToResponse;
import edu.project.c05607477.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @RequestMapping(path = "/transferTo", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public TransferToResponse transferTo(@RequestBody TransferToRequest request) {

        TransferToResponse response = new TransferToResponse();
        return response;
    }
}
