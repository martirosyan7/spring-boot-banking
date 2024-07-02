package com.banking.springbootbanking.utils.enums;

import lombok.Getter;

@Getter
public enum CurrencyType {
    USD("Dollar", "$", 100),
    EUR("Euro", "€", 200),
    GBP("Pound", "£", 300);

    private String name;
    private String symbol;
    private int currencyDigits;

    CurrencyType(String name, String symbol, int currencyDigits) {
        this.name = name;
        this.symbol = symbol;
        this.currencyDigits = currencyDigits;
    }
}

