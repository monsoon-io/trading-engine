package com.trading.engine.domain.instrument;

import java.util.Objects;

public class Instrument {
    private final String symbol;
    private final String name;

    public Instrument(String symbol, String name) {
        if(symbol == null)
            throw new IllegalArgumentException("symbol cannot be null");
        else if(symbol.isBlank())
            throw new IllegalArgumentException("symbol cannot be empty");
        if(name == null)
            throw new IllegalArgumentException("name cannot be null");
        else if(name.isBlank())
            throw new IllegalArgumentException("name cannot be empty");

        this.symbol = symbol;
        this.name = name;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public String getName() {
        return this.name;
    }
}