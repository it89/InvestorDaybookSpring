package com.github.it89.investordaybookspring.daybook.stockmarket;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public final class DealStock extends Deal {
    private static final Map<String, DealStock> objects = new HashMap<>();
    private BigDecimal price;

    private DealStock(String dealNumber, SecurityStock stock) {
        super(dealNumber);
        security = stock;
    }

    public static DealStock getInstance(String dealNumber, SecurityStock stock) {
        if (objects.containsKey(dealNumber)) {
            return objects.get(dealNumber);
        } else {
            return new DealStock(dealNumber, stock);
        }
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "DealStock{" +
                "price=" + price +
                ", security=" + security +
                //", requestNumber='" + requestNumber + '\'' +
                ", dealNumber='" + dealNumber + '\'' +
                ", dateTime=" + dateTime +
                ", operation=" + operation +
                ", amount=" + amount +
                ", volume=" + volume +
                ", commission=" + commission +
                '}';
    }
}
