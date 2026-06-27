package com.trading.engine.domain.holding;

import com.trading.engine.domain.account.Account;
import com.trading.engine.domain.instrument.Instrument;

public class Holding {
    private final String holdingId;
    private final Account account;
    private final Instrument instrument;
    private long quantity;

    public Holding(String holdingId, Account account, Instrument instrument, long quantity) {
        if(holdingId == null)
            throw new IllegalArgumentException("holdingId cannot be null");
        if(holdingId.isBlank())
            throw new IllegalArgumentException("holdingId cannot be blank");
        if(account == null)
            throw new IllegalArgumentException("account cannot be null");
        if(instrument == null)
            throw new IllegalArgumentException("instrument cannot be null");
        if(quantity <= 0)
            throw new IllegalArgumentException("quantity should be greater than zero");

        this.holdingId = holdingId;
        this.account = account;
        this.instrument = instrument;
        this.quantity = quantity;
    }

    public String getHoldingId() {
        return holdingId;
    }

    public Account getAccount() {
        return account;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public long getQuantity() {
        return quantity;
    }

    public void addQuantity(long quantity) {
        if(quantity <= 0)
            throw new IllegalArgumentException("quantity should be greater than zero");

        this.quantity = this.quantity + quantity;
    }

    public void reduceQuantity(long quantity) {
        if(quantity <= 0)
            throw new IllegalArgumentException("quantity should be greater than zero");
        if(quantity > this.quantity)
            throw new IllegalArgumentException("insufficient quantity");

        this.quantity = this.quantity - quantity;
    }
}