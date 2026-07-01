package com.trading.engine.matching;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OrderBookManager {
    private final Map<String, OrderBook> orderBooksMap = new ConcurrentHashMap<>();

    /**
     * Gets order book based on symbol
     * @param symbol should never be null
     * @return the existing OrderBook for the symbol, or a newly created one if none existed.
     */
    public OrderBook getOrderBook(String symbol) {
        return orderBooksMap.computeIfAbsent(symbol, key -> new OrderBook());
    }
}
