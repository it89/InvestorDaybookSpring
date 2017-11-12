package com.github.it89.investordaybookspring.main;

import com.github.it89.investordaybookspring.dao.interfaces.DealDAO;
import com.github.it89.investordaybookspring.dao.interfaces.SecurityDAO;
import com.github.it89.investordaybookspring.daybook.exp.xls.ExportXLS;
import com.github.it89.investordaybookspring.daybook.imp.xml.ImportXMLOpenBroker;
import com.github.it89.investordaybookspring.daybook.stockmarket.Deal;
import com.github.it89.investordaybookspring.daybook.stockmarket.Security;
import com.github.it89.investordaybookspring.daybook.stockmarket.SecurityStock;
import com.github.it89.investordaybookspring.daybook.stockmarket.SecurityType;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Run {
    public static SecurityDAO securityDAO = null;
    public static DealDAO dealDAO = null;
    public static void main(String[] args) {
        new Run().run();
    }

    private void run() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        securityDAO = context.getBean(SecurityDAO.class);
        dealDAO = context.getBean(DealDAO.class);

        /*ImportXMLOpenBroker imp = new ImportXMLOpenBroker();
        imp.importXML("F:\\TMP\\OB\\Broker_Report_FB422036-05C1-49D6-AD99-D74CD9A729FA.xml");

        Security security = Security.findByCodeGRN("1-01-60525-P");
        List<Deal> dealList = dealDAO.getListBySecurity(security);
        System.out.println(security);
        for (Deal deal : dealList) {
            System.out.println(deal);
        }*/
        ImportXMLOpenBroker imp = new ImportXMLOpenBroker();
        File dir = new File("F:\\TMP\\OB\\XML");
        for (File item : dir.listFiles()) {
            imp.importXML(item.getPath());
        }

        ExportXLS exportXLS = new ExportXLS("F:\\TMP\\Deals.xlsx");

        try {
            exportXLS.ExportDeals();
        } catch (IOException e) {
            e.printStackTrace();
        }

        context.close();
    }
}