package com.github.it89.investordaybookspring.dao.entities;

import com.github.it89.investordaybookspring.daybook.stockmarket.Security;
import com.github.it89.investordaybookspring.daybook.stockmarket.SecurityType;

import javax.persistence.*;

@Entity
@Table(name="security",
        uniqueConstraints = {@UniqueConstraint(columnNames={"isin"})},
        indexes = { @Index(columnList = "isin", name = "idx_secutiry_isin")})
public class SecurityEntity {
    private long id;
    private String isin;
    private String code;
    private String caption;
    private SecurityType type;

    public SecurityEntity(){};

    public SecurityEntity(Security security) {
        isin = security.getIsin();
        code = security.getTicker();
        caption = security.getCaption();
        type = security.getType();
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
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
}
