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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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

        for (int i = 0; i < nodes.getLength(); i++) {
            Node n = nodes.item(i);
            NamedNodeMap nodeMap = n.getAttributes();

            Security security;
            Node securityGRNCode = nodeMap.getNamedItem("security_grn_code");
            if (securityGRNCode != null) {
                security = Security.findByCodeGRN(securityGRNCode.getTextContent());
            } else {
                String securityCaption = nodeMap.getNamedItem("security_name").getTextContent();
                security = Security.findByCaption(securityCaption);
            }

            String dealNumber = nodeMap.getNamedItem("deal_no").getTextContent();
            LocalDateTime dateTime = LocalDateTime.parse(nodeMap.getNamedItem("conclusion_time").getTextContent());

            long amount;
            TradeOperation operation;
            Node buyQnty = nodeMap.getNamedItem("buy_qnty");
            if (buyQnty != null) {
                operation = TradeOperation.BUY;
                amount = Double.valueOf(buyQnty.getTextContent()).longValue();
            } else {
                operation = TradeOperation.SELL;
                amount = Double.valueOf(nodeMap.getNamedItem("sell_qnty").getTextContent()).longValue();
            }

            BigDecimal volume = new BigDecimal(nodeMap.getNamedItem("volume_currency").getTextContent());
            BigDecimal commission = new BigDecimal(nodeMap.getNamedItem("broker_commission").getTextContent());

            Deal.Builder builder = new Deal.Builder(dealNumber, security).dateTime(dateTime).operation(operation)
                    .amount(amount).volume(volume).commission(commission);

            if (security.getType() == SecurityType.STOCK) {
                builder = builder.price(new BigDecimal(nodeMap.getNamedItem("price").getTextContent()));
            } else if (security.getType() == SecurityType.BOND) {
                builder = builder.pricePct(new BigDecimal(nodeMap.getNamedItem("price").getTextContent()));
                builder = builder.accumulatedCouponYield(new BigDecimal(nodeMap.getNamedItem("nkd").getTextContent()));
            } else {
                throw new XPathExpressionException("Not valid XML");
            }

            Deal deal = builder.build();
            deal.save();
        }
    }
}
