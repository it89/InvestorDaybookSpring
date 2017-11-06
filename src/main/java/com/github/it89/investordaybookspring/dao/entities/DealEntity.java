package com.github.it89.investordaybookspring.dao.entities;

import com.github.it89.investordaybookspring.dao.impls.SecurityDAOImpl;
import com.github.it89.investordaybookspring.dao.interfaces.SecurityDAO;
import com.github.it89.investordaybookspring.daybook.stockmarket.*;
import com.github.it89.investordaybookspring.main.Run;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="deal")
public class DealEntity {
    private long id;
    private SecurityEntity security;
    private String dealNumber;
    private LocalDateTime dateTime;
    private TradeOperation operation;
    private long amount;
    private BigDecimal volume;
    private BigDecimal commission;
    private BigDecimal price;
    private BigDecimal pricePct;
    private BigDecimal accumulatedCouponYield;

    public DealEntity() {}

    public DealEntity(Deal deal) {
        SecurityDAO securityDAO = Run.securityDAO;
        security = securityDAO.getEntityByISIN(deal.getSecurity().getIsin());
        dealNumber = deal.getDealNumber();
        dateTime = deal.getDateTime();
        operation = deal.getOperation();
        amount = deal.getAmount();
        volume = deal.getVolume();
        commission = deal.getCommission();
        if (deal instanceof DealStock) {
            price = ((DealStock) deal).getPrice();
        } else if (deal instanceof DealBond) {
            pricePct = ((DealBond) deal).getPricePct();
            accumulatedCouponYield = ((DealBond) deal).getAccumulatedCouponYield();
        }
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "security_id")
    public SecurityEntity getSecurity() {
        return security;
    }

    public void setSecurity(SecurityEntity security) {
        this.security = security;
    }

    @Column(name = "deal_number", nullable = false)
    public String getDealNumber() {
        return dealNumber;
    }

    public void setDealNumber(String dealNumber) {
        this.dealNumber = dealNumber;
    }

    @Column(name = "date_time")
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Column(name = "operation")
    @Enumerated(EnumType.STRING)
    public TradeOperation getOperation() {
        return operation;
    }

    public void setOperation(TradeOperation operation) {
        this.operation = operation;
    }

    @Column(name = "amount")
    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    @Column(name = "volume")
    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    @Column(name = "comission")
    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    @Column(name = "price")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Column(name = "price_pct")
    public BigDecimal getPricePct() {
        return pricePct;
    }

    public void setPricePct(BigDecimal pricePct) {
        this.pricePct = pricePct;
    }

    @Column(name = "accumulated_coupon_yield")
    public BigDecimal getAccumulatedCouponYield() {
        return accumulatedCouponYield;
    }

    public void setAccumulatedCouponYield(BigDecimal accumulatedCouponYield) {
        this.accumulatedCouponYield = accumulatedCouponYield;
    }

    public Deal toDeal() {
        Security securityPOJO = security.toSecurity()  ;
        Deal.Builder builder = new Deal.Builder(dealNumber, securityPOJO).dateTime(dateTime)
                .operation(operation).amount(amount).volume(volume).commission(commission);
        if (securityPOJO.getType() == SecurityType.STOCK) {
            builder = builder.price(price);
        } else if (securityPOJO.getType() == SecurityType.BOND) {
            builder = builder.pricePct(pricePct).accumulatedCouponYield(accumulatedCouponYield);
        } else {
            throw new AssertionError("Unknown type of Security");
        }
        return builder.build();
    }
}
