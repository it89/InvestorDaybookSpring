package com.github.it89.investordaybookspring.dao.entities;

import com.github.it89.investordaybookspring.daybook.stockmarket.Security;
import com.github.it89.investordaybookspring.daybook.stockmarket.SecurityType;

import javax.persistence.*;

@Entity
@Table(name="security",
        uniqueConstraints = {@UniqueConstraint(columnNames={"isin"})},
        indexes = { @Index(columnList = "isin", name = "idx_secutiry_isin"),
                @Index(columnList = "code_grn", name = "idx_secutiry_code_grn")})
public class SecurityEntity {
    private long id;
    private String isin;
    private String ticker;
    private String caption;
    private SecurityType type;
    private String codeGRN;

    public SecurityEntity(){};

    public SecurityEntity(Security security) {
        isin = security.getIsin();
        ticker = security.getTicker();
        caption = security.getCaption();
        type = security.getType();
        codeGRN = security.getCodeGRN();
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

    @Column(name = "isin", nullable = false)

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    @Column(name = "ticker")
    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    @Column(name = "caption")
    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    public SecurityType getType() {
        return type;
    }

    public void setType(SecurityType type) {
        this.type = type;
    }

    @Column(name = "code_grn")
    public String getCodeGRN() {
        return codeGRN;
    }

    public void setCodeGRN(String codeGRN) {
        this.codeGRN = codeGRN;
    }

    public Security toSecurity() {
        return new Security.Builder(isin, type, ticker, caption).codeGRN(codeGRN).build();
    }
}
