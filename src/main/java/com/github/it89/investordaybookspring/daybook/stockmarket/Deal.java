package com.github.it89.investordaybookspring.daybook.stockmarket;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public abstract class Deal {
    protected Security security;
    protected final String dealNumber;
    protected LocalDate date;
    protected LocalTime time;
    protected TradeOperation operation;
    protected long amount;
    protected BigDecimal volume;
    protected BigDecimal commission;

    public static class Builder {
        private final Security security;
        private final String dealNumber;
        private LocalDate date;
        private LocalTime time;
        private TradeOperation operation;
        private long amount;
        private BigDecimal volume;
        private BigDecimal commission;
        private BigDecimal price;
        private BigDecimal pricePct;
        private BigDecimal accumulatedCouponYield;

        public Builder(Security security, String dealNumber) {
            this.security = security;
            this.dealNumber = dealNumber;
        }

        public Builder date(LocalDate date) {
            this.date = date;
            return this;
        }

        public Builder time(LocalTime time) {
            this.time = time;
            return this;
        }

        public Builder operation(TradeOperation operation) {
            this.operation = operation;
            return this;
        }

        public Builder operation(long amount) {
            this.amount = amount;
            return this;
        }

        public Builder volume(BigDecimal volume) {
            this.volume = volume;
            return this;
        }

        public Builder commission(BigDecimal commission) {
            this.commission = commission;
            return this;
        }

        public Builder price (BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder pricePct (BigDecimal pricePct) {
            this.pricePct = pricePct;
            return this;
        }

        public Builder accumulatedCouponYield (BigDecimal accumulatedCouponYield) {
            this.accumulatedCouponYield = accumulatedCouponYield;
            return this;
        }

        public Deal build() {
            Deal deal;
            if (security.getType() == SecurityType.STOCK) {
                DealStock stock = DealStock.getInstance(dealNumber, (SecurityStock)security);
                stock.setPrice(price);
                deal = stock;
            } else if (security.getType() == SecurityType.BOND) {
                DealBond bond = DealBond.getInstance(dealNumber, (SecurityBond)security);
                bond.setPricePct(pricePct);
                bond.setAccumulatedCouponYield(accumulatedCouponYield);
                deal = bond;
            } else {
                throw new IllegalStateException("Unknown type of Security");
            }
            deal.setDate(date);
            deal.setTime(time);
            deal.setOperation(operation);
            deal.setAmount(amount);
            deal.setVolume(volume);
            deal.setCommission(commission);
            return deal;
        }
    }

    public Deal(String dealNumber) {
        this.dealNumber = dealNumber;
    }

    public Security getSecurity() {
        return security;
    }


    public String getDealNumber() {
        return dealNumber;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public TradeOperation getOperation() {
        return operation;
    }

    public void setOperation(TradeOperation operation) {
        this.operation = operation;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Deal deal = (Deal) o;

        return dealNumber.equals(deal.dealNumber);
    }

    @Override
    public int hashCode() {
        return dealNumber.hashCode();
    }
}
