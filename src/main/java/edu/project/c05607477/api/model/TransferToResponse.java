package edu.project.c05607477.api.model;

import edu.project.c05607477.jpa.entity.TxStatus;

public class TransferToResponse {

    private String txNo;

    private TxStatus txStatus;

    public String getTxNo() {
        return txNo;
    }

    public void setTxNo(String txNo) {
        this.txNo = txNo;
    }

    public TxStatus getTxStatus() {
        return txStatus;
    }

    public void setTxStatus(TxStatus txStatus) {
        this.txStatus = txStatus;
    }
}
