package com.banking.springbootbanking.utils.enums;

public enum CardType {
    VISA("Visa"),
    MASTERCARD("MasterCard"),
    MAESTRO("Maestro"),
    AMEX("American Express");

    private final String name;

    CardType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

