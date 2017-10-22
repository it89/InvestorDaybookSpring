package com.github.it89.investordaybookspring.daybook.stockmarket;

import java.math.BigDecimal;

public class BondDeal extends Deal {
    private BigDecimal pricePct;
    private BigDecimal accumulatedCouponYield;

    public BondDeal(SecurityBond bond, String dealNumber) {
        super(dealNumber);
        security = bond;
    }

    public BigDecimal getPricePct() {
        return pricePct;
    }

    public void setPricePct(BigDecimal pricePct) {
        this.pricePct = pricePct;
    }

    public BigDecimal getAccumulatedCouponYield() {
        return accumulatedCouponYield;
    }

    public void setAccumulatedCouponYield(BigDecimal accumulatedCouponYield) {
        this.accumulatedCouponYield = accumulatedCouponYield;
    }

    @Override
    public String toString() {
        return "BondDeal{" +
                "pricePct=" + pricePct +
                ", accumulatedCouponYield=" + accumulatedCouponYield +
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
