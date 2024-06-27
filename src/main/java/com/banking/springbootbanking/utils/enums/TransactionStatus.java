package com.banking.springbootbanking.utils.enums;

public enum TransactionStatus {
    PENDING("Pending"),
    COMPLETED("Completed"),
    FAILED("Failed");

    private final String name;

    TransactionStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
