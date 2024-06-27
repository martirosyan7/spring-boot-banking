package com.banking.springbootbanking.utils.enums;

public enum CurrencyType {
    USD("Dollar", "$"),
    EUR("Euro", "€");

    private String name;
    private String symbol;

    CurrencyType(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }
}

