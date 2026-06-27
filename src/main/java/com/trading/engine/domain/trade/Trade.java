package com.trading.engine.domain.trade;

import com.trading.engine.domain.order.Order;

import java.math.BigDecimal;
import java.time.Instant;

public class Trade {
    private final String tradeId;
    private final Order buyOrder;
    private final Order sellOrder;
    private final long quantity;
    private final BigDecimal price;
    private final Instant timeStamp;

    public Trade(String tradeId, Order buyOrder, Order sellOrder, long quantity, BigDecimal price) {
        if(tradeId == null)
            throw new IllegalArgumentException("tradeId cannot be null");
        if(tradeId.isBlank())
            throw new IllegalArgumentException("tradeId cannot be blank");
        if(buyOrder == null)
            throw new IllegalArgumentException("buyOrder cannot be null");
        if(sellOrder == null)
            throw new IllegalArgumentException("sellOrder cannot be null");
        if(quantity <= 0)
            throw new IllegalArgumentException("quantity should be greater than zero");
        if(price == null)
            throw new IllegalArgumentException("price cannot be null");
        else if(price.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("price should be greater than zero");

        this.tradeId = tradeId;
        this.buyOrder = buyOrder;
        this.sellOrder = sellOrder;
        this.quantity = quantity;
        this.price = price;
        this.timeStamp = Instant.now();
    }

    public String getTradeId() {
        return tradeId;
    }

    public Order getBuyOrder() {
        return buyOrder;
    }

    public Order getSellOrder() {
        return sellOrder;
    }

    public long getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Instant getTimeStamp() {
        return timeStamp;
    }
}