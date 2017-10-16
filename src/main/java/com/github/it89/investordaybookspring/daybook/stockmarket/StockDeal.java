package com.github.it89.investordaybookspring.daybook.stockmarket;

import java.math.BigDecimal;

public class StockDeal extends Deal {
    private BigDecimal price;

    public StockDeal(Stock stock, String dealNumber) {
        super(dealNumber);
        security = stock;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "StockDeal{" +
                "price=" + price +
                ", security=" + security +
                //", requestNumber='" + requestNumber + '\'' +
                ", dealNumber='" + dealNumber + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", operation=" + operation +
                ", amount=" + amount +
                ", volume=" + volume +
                ", commission=" + commission +
                '}';
    }
}
