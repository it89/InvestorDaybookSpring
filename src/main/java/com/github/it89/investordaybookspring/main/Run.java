package com.github.it89.investordaybookspring.main;

import com.github.it89.investordaybookspring.daybook.imp.xml.ImportXMLOpenBroker;
import com.github.it89.investordaybookspring.daybook.stockmarket.SecurityStock;
import com.github.it89.investordaybookspring.daybook.stockmarket.SecurityType;

public class Run {
    public static void main(String[] args) {
        new Run().run();
    }

    private void run() {
        ImportXMLOpenBroker imp = new ImportXMLOpenBroker();
        imp.importXML("F:\\TMP\\OB\\Broker_Report_FB422036-05C1-49D6-AD99-D74CD9A729FA.xml");


        /*SecurityStock securityStock = new SecurityStock("RU4574", SecurityType.STOCK,"GMK", "Норникель2");
        securityStock.save();*/

    }
}