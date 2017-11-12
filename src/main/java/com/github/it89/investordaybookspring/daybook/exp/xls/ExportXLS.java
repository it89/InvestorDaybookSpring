package com.github.it89.investordaybookspring.daybook.exp.xls;

import com.github.it89.investordaybookspring.daybook.stockmarket.*;
import com.github.it89.investordaybookspring.main.Run;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExportXLS {
    private final  String filename;

    public ExportXLS(String filename) {
        this.filename = filename;
    }

    public void ExportDeals() throws IOException {
        final int NUMER_COL = 0;
        final int CECURITY_NAME_COL = 1;
        final int DATE_COL = 2;
        final int AMOUNT_COL = 3;
        final int PRICE_COL = 4;
        final int VOLUME_COL = 5;
        final int COMISSION_COL = 6;
        final int NKD_COL = 7;

        Workbook book = new XSSFWorkbook();
        Sheet sheet = book.createSheet("Deals");

        int rowNumber = 0;
        Row row = sheet.createRow(rowNumber);
        row.createCell(NUMER_COL).setCellValue("Number");
        row.createCell(CECURITY_NAME_COL).setCellValue("Security");
        row.createCell(DATE_COL).setCellValue("Date");
        row.createCell(AMOUNT_COL).setCellValue("Amount");
        row.createCell(PRICE_COL).setCellValue("Price");
        row.createCell(VOLUME_COL).setCellValue("Volume");
        row.createCell(COMISSION_COL).setCellValue("Comission");
        row.createCell(NKD_COL).setCellValue("NKD");

        Security security = Security.findByCodeGRN("1-01-60525-P");
        List<Deal> dealList = Deal.getList();
        Collections.sort(dealList);

        for (Deal deal : dealList) {
            long amount = deal.getAmount();
            BigDecimal price;
            BigDecimal nkd = null;
            if (deal instanceof DealStock) {
                price = ((DealStock) deal).getPrice();
            } else {
                price = ((DealBond) deal).getPricePct();
                nkd = ((DealBond) deal).getAccumulatedCouponYield().negate();
            }
            BigDecimal volume = deal.getVolume();

            if(deal.getOperation() == TradeOperation.SELL) {
                amount = -amount;
                volume = volume.negate();
                if (nkd != null)
                    nkd = nkd.negate();
            }

            row = sheet.createRow(++rowNumber);
            row.createCell(NUMER_COL).setCellValue(deal.getDealNumber());
            row.createCell(CECURITY_NAME_COL).setCellValue(deal.getSecurity().getCaption());
            row.createCell(DATE_COL).setCellValue(deal.getDateTime().toString());
            row.createCell(AMOUNT_COL).setCellValue(amount);
            row.createCell(PRICE_COL).setCellValue(price.doubleValue());
            row.createCell(VOLUME_COL).setCellValue(volume.doubleValue());
            row.createCell(COMISSION_COL).setCellValue(deal.getCommission().negate().doubleValue());
            if (nkd != null)
                row.createCell(NKD_COL).setCellValue(nkd.doubleValue());
        }

        for(int i = 0; i <= NKD_COL; i++)
            sheet.autoSizeColumn(i);

        book.write(new FileOutputStream(filename));
        book.close();
    }
}

