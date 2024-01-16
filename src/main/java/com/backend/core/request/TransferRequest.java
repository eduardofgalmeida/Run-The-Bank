package com.backend.core.request;


import java.math.BigDecimal;

public class TransferRequest {

    private String sourceAccountAgency;
    private String targetAccountAgency;
    private BigDecimal amount;

    public TransferRequest() {
    }

    public TransferRequest(String sourceAccountAgency, String targetAccountAgency, BigDecimal amount) {
        this.sourceAccountAgency = sourceAccountAgency;
        this.targetAccountAgency = targetAccountAgency;
        this.amount = amount;
    }

    public String getSourceAccountAgency() {
        return sourceAccountAgency;
    }

    public void setSourceAccountId(Long sourceAccountId) {
    }

    public String getTargetAccountAgency() {
        return targetAccountAgency;
    }

    public void setTargetAccountAgency(String targetAccountAgency) {
        this.targetAccountAgency = targetAccountAgency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
