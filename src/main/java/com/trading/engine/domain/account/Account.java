package com.trading.engine.domain.account;

import java.math.BigDecimal;

public class Account {
    private final String accountId;
    private final String ownerName;
    private BigDecimal balance;

    public Account(String accountId, String ownerName, BigDecimal balance) {
        if(accountId == null)
            throw new IllegalArgumentException("accountId cannot be null");
        else if(accountId.isBlank())
            throw new IllegalArgumentException("accountId cannot be empty");
        if(ownerName == null)
            throw new IllegalArgumentException("ownerName cannot be null");
        else if(ownerName.isBlank())
            throw new IllegalArgumentException("ownerName cannot be empty");
        if(balance == null)
            throw new IllegalArgumentException("balance cannot be null");
        else if(balance.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("balance cannot be negative");

        this.accountId = accountId;
        this.ownerName = ownerName;
        this.balance = balance;
    }

    public String getAccountId() {
        return this.accountId;
    }

    public String getOwnerName() {
        return this.ownerName;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    private void setBalance(BigDecimal amount) {
        this.balance = amount;
    }

    public void deposit(BigDecimal amount) {
        if(amount == null)
            throw new IllegalArgumentException("amount cannot be null");
        else if(amount.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("amount cannot be negative");

        this.balance = this.balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        if(amount == null)
            throw new IllegalArgumentException("amount cannot be null");
        else if(amount.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("amount cannot be negative");
        else if (amount.compareTo(this.balance) > 0)
            throw new IllegalArgumentException("amount to be withdrawn cannot be grater than balance");

        this.balance = this.balance.subtract(amount);
    }
}
