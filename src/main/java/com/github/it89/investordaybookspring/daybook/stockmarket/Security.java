package com.github.it89.investordaybookspring.daybook.stockmarket;


import com.github.it89.investordaybookspring.dao.entities.SecurityEntity;
import com.github.it89.investordaybookspring.dao.interfaces.SecurityDAO;
import org.jetbrains.annotations.Nullable;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;

public abstract class Security {
    protected final String isin;
    protected final SecurityType type;
    protected String ticker;
    protected String caption;
    @Nullable
    protected String codeGRN;

    public static class Builder {
        private final String isin;
        private final SecurityType type;
        private final String ticker;
        private final String caption;
        private String codeGRN = null;

        public Builder(String isin, SecurityType type, String ticker, String caption) {
            this.isin = isin;
            this.type = type;
            this.ticker = ticker;
            this.caption = caption;
        }

        public Builder codeGRN(String codeGRN) {
            this.codeGRN = codeGRN;
            return this;
        }

        public Security build() {
            Security security;
            if (type == SecurityType.STOCK) {
                SecurityStock stock = SecurityStock.getInstance(isin);
                security = stock;
            } else if (type == SecurityType.BOND) {
                SecurityBond bond = SecurityBond.getInstance(isin);
                security = bond;
            } else {
                throw new IllegalStateException("Unknown type of Security");
            }
            security.setTicker(ticker);
            security.setCaption(caption);
            security.setCodeGRN(codeGRN);

            return security;
        }
    }

    protected Security(String isin, SecurityType type) {
        this.isin = isin;
        this.type = type;
    }

    public String getIsin() {
        return isin;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public SecurityType getType() {
        return type;
    }

    public String getCodeGRN() {
        return codeGRN;
    }

    public void setCodeGRN(String codeGRN) {
        this.codeGRN = codeGRN;
    }

    @Override
    public String toString() {
        return "Security{" +
                "isin='" + isin + '\'' +
                ", type=" + type +
                ", ticker='" + ticker + '\'' +
                ", caption='" + caption + '\'' +
                ", codeGRN='" + codeGRN + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Security security = (Security) o;

        return isin.equals(security.isin);
    }

    @Override
    public int hashCode() {
        return isin.hashCode();
    }

    public void save() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        SecurityDAO securityDAO = context.getBean(SecurityDAO.class);
        SecurityEntity securityEntity = new SecurityEntity(this);
        securityDAO.merge(securityEntity);
        context.close();
    }

    public static Security findByCodeGRN(String codeGRN) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        SecurityDAO securityDAO = context.getBean(SecurityDAO.class);
        SecurityEntity securityEntity = securityDAO.getEntityByCodeGRN(codeGRN);
        Security security = securityEntity.toSecurity();
        context.close();
        return security;
    }
}
