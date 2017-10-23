package com.github.it89.investordaybookspring.main;

import com.github.it89.investordaybookspring.dao.interfaces.SecurityDAO;
import com.github.it89.investordaybookspring.daybook.imp.xml.ImportXMLOpenBroker;
import com.github.it89.investordaybookspring.daybook.stockmarket.Security;
import com.github.it89.investordaybookspring.daybook.stockmarket.SecurityStock;
import com.github.it89.investordaybookspring.daybook.stockmarket.SecurityType;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Run {
    public static SecurityDAO securityDAO = null;
    public static void main(String[] args) {
        new Run().run();
    }

    private void run() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        securityDAO = context.getBean(SecurityDAO.class);

        ImportXMLOpenBroker imp = new ImportXMLOpenBroker();
        imp.importXML("F:\\TMP\\OB\\Broker_Report_FB422036-05C1-49D6-AD99-D74CD9A729FA.xml");

        Security security = Security.findByCodeGRN("1-01-60525-P");
        System.out.println(security);

        context.close();
    }
}