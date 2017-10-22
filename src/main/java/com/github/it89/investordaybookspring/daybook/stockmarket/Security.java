package com.github.it89.investordaybookspring.daybook.stockmarket;


import com.github.it89.investordaybookspring.dao.entities.SecurityEntity;
import com.github.it89.investordaybookspring.dao.interfaces.SecurityDAO;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class Security {
    protected final String isin;
    protected final SecurityType type;
    protected String ticker;
    protected String caption;

    public Security(String isin, SecurityType type, String ticker, String caption) {
        this.isin = isin;
        this.type = type;
        this.ticker = ticker;
        this.caption = caption;
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

    @Override
    public String toString() {
        return "Security{" +
                "isin='" + isin + '\'' +
                ", ticker='" + ticker + '\'' +
                ", caption='" + caption + '\'' +
                ", type=" + type +
                '}';
    }

    public void save() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        SecurityDAO securityDAO = context.getBean(SecurityDAO.class);
        SecurityEntity securityEntity = new SecurityEntity(this);
        securityDAO.merge(securityEntity);
        context.close();
    }
}
