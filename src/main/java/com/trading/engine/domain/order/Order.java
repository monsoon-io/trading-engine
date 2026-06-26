package com.trading.engine.domain.order;

import com.trading.engine.domain.instrument.Instrument;

import java.math.BigDecimal;
import java.time.Instant;

public class Order {
    private final String orderId;
    private final Instrument instrument;
    private final OrderSide side;
    private final BigDecimal price;
    private final long quantity;
    private final Instant placedAt;
    private OrderStatus status;
    private long remainingQuantity;

    public Order(String orderId, Instrument instrument, OrderSide side, BigDecimal price, long quantity) {
        if(orderId == null)
            throw new IllegalArgumentException("orderId cannot be null");
        else if (orderId.isBlank())
            throw new IllegalArgumentException("orderId cannot be blank");
        if(instrument == null)
            throw new IllegalArgumentException("instrument cannot be null");
        if(side == null)
            throw new IllegalArgumentException("side cannot be null");
        if(price == null)
            throw new IllegalArgumentException("price cannot be null");
        else if(price.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("price should be greater than zero");
        if(quantity <= 0)
            throw new IllegalArgumentException("quantity should be greater than zero");

        this.orderId = orderId;
        this.instrument = instrument;
        this.side = side;
        this.price = price;
        this.quantity = quantity;
        this.placedAt = Instant.now();
        this.status = OrderStatus.PENDING;
        this.remainingQuantity = quantity;
    }

    public String getOrderId() {
        return orderId;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public OrderSide getSide() {
        return side;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public long getQuantity() {
        return quantity;
    }

    public Instant getPlacedAt() {
        return placedAt;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public long getRemainingQuantity() {
        return remainingQuantity;
    }

    public void transitionTo(OrderStatus newStatus) {
        if(newStatus == null)
            throw new IllegalArgumentException("newStatus cannot be null");
        if(OrderStatus.FILLED.equals(this.status) || OrderStatus.CANCELLED.equals(this.status))
            throw new IllegalStateException("an order cannot transition from terminal status");
        if(OrderStatus.PENDING.equals(this.status) && OrderStatus.PENDING.equals(newStatus))
            throw new IllegalStateException("order is already pending");
        if(OrderStatus.PARTIAL.equals(this.status) && OrderStatus.PENDING.equals(newStatus))
            throw new IllegalStateException("partial orders cannot transition to pending");

        this.status = newStatus;
    }

    public void reduceRemainingQuantity(long filledQuantity) {
        if(this.remainingQuantity == 0)
            throw new IllegalStateException("order is completely filled");
        if(filledQuantity <=0)
            throw new IllegalArgumentException("filledQuantity should be greater than zero");
        if(filledQuantity > remainingQuantity)
            throw new IllegalArgumentException("filledQuantity cannot be greater than remaining quantity");

        this.remainingQuantity = remainingQuantity - filledQuantity;
    }
}
