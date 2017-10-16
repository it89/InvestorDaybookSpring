package com.github.it89.investordaybookspring.daybook.stockmarket;

import com.github.it89.investordaybookspring.dao.interfaces.BondDAO;
import com.github.it89.investordaybookspring.dao.interfaces.StockDAO;
import com.github.it89.investordaybookspring.daybook.interfaces.Security;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Bond implements Security {
    private final String code;
    private String caption;

    public Bond(String code, String caption) {
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
        BondDAO bondDAO = (BondDAO) context.getBean("sqliteBondDAO");

        if (!(bondDAO.exists(this))) {
            bondDAO.register(this);
        }
    }

    public static Bond getBondByCaption(String caption) {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        BondDAO bondDAO = (BondDAO) context.getBean("sqliteBondDAO");

        return bondDAO.getBondByCaption(caption);
    }
}
