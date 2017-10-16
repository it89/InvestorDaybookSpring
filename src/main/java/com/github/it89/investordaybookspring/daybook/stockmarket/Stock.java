package com.github.it89.investordaybookspring.daybook.stockmarket;

import com.github.it89.investordaybookspring.dao.interfaces.StockDAO;
import com.github.it89.investordaybookspring.daybook.interfaces.Security;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Stock implements Security {
    private final String code;
    private String caption;

    public Stock(String code, String caption) {
        this.code = code;
        this.caption = caption;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    @Override
    public void registerNew() {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        StockDAO stockDAO = (StockDAO) context.getBean("sqliteStockDAO");

        if (!(stockDAO.exists(this))) {
            stockDAO.register(this);
        }
    }

    public static Stock getStockByCaption(String caption) {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        StockDAO stockDAO = (StockDAO) context.getBean("sqliteStockDAO");

        return stockDAO.getStockByCaption(caption);
    }
}
