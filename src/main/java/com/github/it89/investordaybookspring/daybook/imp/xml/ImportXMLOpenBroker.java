package com.github.it89.investordaybookspring.daybook.imp.xml;

import com.github.it89.investordaybookspring.daybook.stockmarket.*;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;

public class ImportXMLOpenBroker {
    private XPathFactory pathFactory = XPathFactory.newInstance();
    public void importXML(String fileName) {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(fileName);

            importSecurity(document);
            importMaidDeals(document);
        } catch (ParserConfigurationException | XPathExpressionException| SAXException | IOException ex) {
            ex.printStackTrace(System.out);
        }
    }

    private void importSecurity(Document document) throws XPathExpressionException {
        XPath xpath = pathFactory.newXPath();
        XPathExpression expr = xpath.compile("//spot_portfolio_security_params/item");
        NodeList nodes = (NodeList) expr.evaluate(document, XPathConstants.NODESET);

        for (int i = 0; i < nodes.getLength(); i++) {
            Node n = nodes.item(i);
            NamedNodeMap nodeMap = n.getAttributes();

            String isin = nodeMap.getNamedItem("isin").getTextContent();
            String textSecurityType = nodeMap.getNamedItem("security_type").getTextContent();
            String ticker = nodeMap.getNamedItem("ticker").getTextContent();
            String caption = nodeMap.getNamedItem("security_name").getTextContent().trim();
            SecurityType securityType;
            if (textSecurityType.equalsIgnoreCase("Акции") || textSecurityType.equalsIgnoreCase("GDR")) {
                securityType = SecurityType.STOCK;
            } else if (textSecurityType.equalsIgnoreCase("Облигации")) {
                securityType = SecurityType.BOND;
            } else {
                throw new XPathExpressionException("Not valid XML");
            }
            Node nodeCodeGRN = nodeMap.getNamedItem("security_grn_code");
            String codeGRN = null;
            if (nodeCodeGRN != null) {
                codeGRN = nodeCodeGRN.getTextContent();
            }

            Security security = new Security.Builder(isin, securityType, ticker, caption).codeGRN(codeGRN).build();
            security.save();
        }
    }

    private void importMaidDeals(Document document) throws XPathExpressionException {
        XPath xpath = pathFactory.newXPath();
        XPathExpression expr = xpath.compile("//spot_main_deals_conclusion/item");
        NodeList nodes = (NodeList) expr.evaluate(document, XPathConstants.NODESET);

        /*for (int i = 0; i < nodes.getLength(); i++) {
            Node n = nodes.item(i);
            NamedNodeMap nodeMap = n.getAttributes();

            String securityType = nodeMap.getNamedItem("security_name").getTextContent().trim();
            Security security = SecurityStock.getStockByCaption(securityType);
            if (security == null) {
                // TODO: Лучше идентифизировать по коду, а не по тикуру или названию. Для этого нужно переделать Security
                // TODO: Уже добавили код GRN, но для GDR его нет и придется определять по наименованию
                security = SecurityBond.getBondByCaption(securityType);
                if (security == null) {
                    throw new XPathExpressionException("Not valid XML");
                }
            }

            String dealNumber = nodeMap.getNamedItem("deal_no").getTextContent();
            Deal deal;
            if (security instanceof SecurityStock) {
                deal = new StockDeal((SecurityStock) security, dealNumber);
            } else {
                deal = new BondDeal((SecurityBond) security, dealNumber);
            }



        }*/
    }
}
