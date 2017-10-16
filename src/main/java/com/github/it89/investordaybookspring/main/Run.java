package com.github.it89.investordaybookspring.main;

import com.github.it89.investordaybookspring.dao.entities.SomeTableEntity;
import com.github.it89.investordaybookspring.dao.interfaces.SomeTableDAO;
import com.github.it89.investordaybookspring.dao.interfaces.StockDAO;
import com.github.it89.investordaybookspring.daybook.imp.xml.ImportXMLOpenBroker;
import com.github.it89.investordaybookspring.daybook.stockmarket.Stock;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDateTime;
import java.util.List;

public class Run {
    public static void main(String[] args) {
        new Run().run();
    }

    private void run() {
        /*Stock s1 = new Stock("SBER", "Сбербанк");
        Stock s2 = new Stock("UPRO", "Юнипро");

        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        StockDAO stockDAO = (StockDAO) context.getBean("sqliteStockDAO");

        stockDAO.register(s1);
        stockDAO.register(s2);
        stockDAO.remove(s1);
        stockDAO.remove(s2);*/
        //ImportXMLOpenBroker imp = new ImportXMLOpenBroker();
        //imp.importXML("F:\\TMP\\OB\\Broker_Report_FB422036-05C1-49D6-AD99-D74CD9A729FA.xml");

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");

        SomeTableDAO someTableDAO = context.getBean(SomeTableDAO.class);

        SomeTableEntity someTableEntity = new SomeTableEntity();
        someTableEntity.setCode(101);
        someTableEntity.setCaption(LocalDateTime.now().toString());

        someTableDAO.save(someTableEntity);

        List<SomeTableEntity> list = someTableDAO.list();

        for(SomeTableEntity t : list){
            System.out.println("Person List::"+t);
        }

        context.close();
    }
}