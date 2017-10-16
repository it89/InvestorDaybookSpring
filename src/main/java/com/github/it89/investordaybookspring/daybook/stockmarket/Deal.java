package com.github.it89.investordaybookspring.daybook.stockmarket;

import com.github.it89.investordaybookspring.daybook.interfaces.Security;

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
}
