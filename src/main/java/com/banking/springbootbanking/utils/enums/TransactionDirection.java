package com.banking.springbootbanking.utils.enums;

public enum TransactionDirection {
    SEND("Send"),
    RECEIVE("Receive"),
    INCOMING("Incoming");

    private String name;

    TransactionDirection(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
