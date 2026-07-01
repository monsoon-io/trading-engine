package com.trading.engine.matching;

import com.trading.engine.domain.order.Order;
import com.trading.engine.domain.order.OrderSide;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class OrderBook {
    private final TreeMap<BigDecimal, ArrayDeque<Order>> bids;
    private final TreeMap<BigDecimal, ArrayDeque<Order>> asks;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public OrderBook() {
        this.bids = new TreeMap<>(Comparator.reverseOrder());
        this.asks = new TreeMap<>();
    }

    /**
     * Adds an order to the appropriate side.
     * Buy orders are added to bid side and sell orders are added to ask side.
     * @param order must not be null
     */
    public void addOrder(Order order) {
        TreeMap<BigDecimal, ArrayDeque<Order>> map = getOrderMap(order);

        lock.writeLock().lock();

        try {
            map.computeIfAbsent(order.getPrice(), key -> new ArrayDeque<>()).add(order);
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Removes the order for the appropriate side.
     * Buy orders are removed from bid side and sell orders are removed from ask side.
     * If the order is not found in the book, this method returns silently with no effect.
     * @param order must not be null
     */
    public void removeOrder(Order order) {
        TreeMap<BigDecimal, ArrayDeque<Order>> map = getOrderMap(order);

        lock.writeLock().lock();

        try {
            if(map.containsKey(order.getPrice())) {
                ArrayDeque<Order> orders = map.get(order.getPrice());

                orders.remove(order);

                if (orders.isEmpty())
                    map.remove(order.getPrice());
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Returns the highest-priced bid order from the book.
     * Returns Optional.empty() if the bid side is empty.
     */
    public Optional<Order> getBestBid() {
        Optional<Order> bestBid;
        lock.readLock().lock();

        try {
            if(bids.isEmpty())
                bestBid = Optional.empty();
            else
                bestBid = Optional.of(this.bids.firstEntry().getValue().getFirst());
        } finally {
            lock.readLock().unlock();
        }

        return bestBid;
    }

    /**
     * Returns the lowest-priced ask order from the book.
     * Returns Optional.empty() if the ask side is empty.
     */
    public Optional<Order> getBestAsk() {
        Optional<Order> bestAsk;
        lock.readLock().lock();

        try {
            if(asks.isEmpty())
                bestAsk = Optional.empty();
            else
                bestAsk = Optional.of(asks.firstEntry().getValue().getFirst());
        } finally {
            lock.readLock().unlock();
        }

        return bestAsk;
    }

    /**
     * Helper to get appropriate order map based on side
     * @param order must not be null
     */
    private TreeMap<BigDecimal, ArrayDeque<Order>> getOrderMap(Order order) {
        if(OrderSide.BUY == order.getSide())
            return this.bids;
        else
           return this.asks;
    }
}
