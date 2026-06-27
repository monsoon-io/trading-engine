package com.trading.engine.domain.order;

import com.trading.engine.domain.instrument.Instrument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderTest {
    private Order order;
    private Instrument instrument;

    @BeforeEach
    void loadDefaultValues() {
        instrument = new Instrument("GOOG", "Alphabet Inc");
        order = new Order("test-order-1", instrument, OrderSide.BUY, new BigDecimal("334.69"), 10);
    }

    /* Valid Transition Cases */
    @Test
    void shouldTransitionFromPendingToPartial() {
        order.transitionTo(OrderStatus.PARTIAL);

        assertEquals(OrderStatus.PARTIAL, order.getStatus());
    }

    @Test
    void shouldTransitionFromPendingToFilled() {
        order.transitionTo(OrderStatus.FILLED);

        assertEquals(OrderStatus.FILLED, order.getStatus());
    }

    @Test
    void shouldTransitionFromPendingToCancelled() {
        order.transitionTo(OrderStatus.CANCELLED);

        assertEquals(OrderStatus.CANCELLED, order.getStatus());
    }

    @Test
    void shouldTransitionFromPartialToFilled() {
        order.transitionTo(OrderStatus.PARTIAL);
        order.transitionTo(OrderStatus.FILLED);

        assertEquals(OrderStatus.FILLED, order.getStatus());
    }

    @Test
    void shouldTransitionFromPartialToCancelled() {
        order.transitionTo(OrderStatus.PARTIAL);
        order.transitionTo(OrderStatus.CANCELLED);

        assertEquals(OrderStatus.CANCELLED, order.getStatus());
    }

    /* Invalid Transition Cases */
    @Test
    void shouldNotTransitionFromPendingToPending() {
        assertThrows(IllegalStateException.class, () -> {
            order.transitionTo(OrderStatus.PENDING);
        });
    }

    @Test
    void shouldNotTransitionFromPartialToPending() {
        order.transitionTo(OrderStatus.PARTIAL);

        assertThrows(IllegalStateException.class, () -> {
            order.transitionTo(OrderStatus.PENDING);
        });
    }

    @Test
    void shouldNotTransitionFromFilledToPending() {
        order.transitionTo(OrderStatus.FILLED);

        assertThrows(IllegalStateException.class, () -> {
            order.transitionTo(OrderStatus.PENDING);
        });
    }

    @Test
    void shouldNotTransitionFromFilledToPartial() {
        order.transitionTo(OrderStatus.FILLED);

        assertThrows(IllegalStateException.class, () -> {
            order.transitionTo(OrderStatus.PARTIAL);
        });
    }

    @Test
    void shouldNotTransitionFromFilledToCancelled() {
        order.transitionTo(OrderStatus.FILLED);

        assertThrows(IllegalStateException.class, () -> {
            order.transitionTo(OrderStatus.CANCELLED);
        });
    }

    @Test
    void shouldNotTransitionFromCancelledToPending() {
        order.transitionTo(OrderStatus.CANCELLED);

        assertThrows(IllegalStateException.class, () -> {
            order.transitionTo(OrderStatus.PENDING);
        });
    }

    @Test
    void shouldNotTransitionFromCancelledToPartial() {
        order.transitionTo(OrderStatus.CANCELLED);

        assertThrows(IllegalStateException.class, () -> {
            order.transitionTo(OrderStatus.PARTIAL);
        });
    }

    @Test
    void shouldNotTransitionFromCancelledToCancelled() {
        order.transitionTo(OrderStatus.CANCELLED);

        assertThrows(IllegalStateException.class, () -> {
            order.transitionTo(OrderStatus.CANCELLED);
        });
    }
}
