package com.github.it89.investordaybookspring.main;

import com.github.it89.investordaybookspring.dao.entities.SecurityEntity;
import com.github.it89.investordaybookspring.dao.entities.SomeTableEntity;
import com.github.it89.investordaybookspring.dao.interfaces.SecurityDAO;
import com.github.it89.investordaybookspring.dao.interfaces.SomeTableDAO;
import com.github.it89.investordaybookspring.daybook.stockmarket.SecurityStock;
import com.github.it89.investordaybookspring.daybook.stockmarket.SecurityType;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.security.Security;
import java.time.LocalDateTime;
import java.util.List;

public class Run {
    public static void main(String[] args) {
        new Run().run();
    }

    private void run() {
        /*SecurityStock s1 = new SecurityStock("SBER", "Сбербанк");
        SecurityStock s2 = new SecurityStock("UPRO", "Юнипро");

        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        StockDAO stockDAO = (StockDAO) context.getBean("sqliteStockDAO");

        stockDAO.register(s1);
        stockDAO.register(s2);
        stockDAO.remove(s1);
        stockDAO.remove(s2);*/
        //ImportXMLOpenBroker imp = new ImportXMLOpenBroker();
        //imp.importXML("F:\\TMP\\OB\\Broker_Report_FB422036-05C1-49D6-AD99-D74CD9A729FA.xml");


        SecurityStock securityStock = new SecurityStock("RU4574", SecurityType.STOCK,"GMK", "Норникель2");
        securityStock.save();

    }
}