package com.github.it89.investordaybookspring.daybook.stockmarket;

import com.github.it89.investordaybookspring.dao.entities.DealEntity;
import com.github.it89.investordaybookspring.dao.interfaces.DealDAO;
import com.github.it89.investordaybookspring.main.Run;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public final class DealBond extends Deal {
    private static final Map<String, DealBond> objects = new HashMap<>();
    private BigDecimal pricePct;
    private BigDecimal accumulatedCouponYield;

    private DealBond(String dealNumber, SecurityBond bond) {
        super(dealNumber);
        security = bond;
    }

    public static DealBond getInstance(String dealNumber, SecurityBond bond) {
        if (objects.containsKey(dealNumber)) {
            return objects.get(dealNumber);
        } else {
            return new DealBond(dealNumber, bond);
        }
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
        return "DealBond{" +
                "pricePct=" + pricePct +
                ", accumulatedCouponYield=" + accumulatedCouponYield +
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

    @Override
    public void save() {
        DealDAO dealDAO = Run.dealDAO;
        DealEntity dealEntity = new DealEntity(this);
        dealDAO.merge(dealEntity);
    }
}
