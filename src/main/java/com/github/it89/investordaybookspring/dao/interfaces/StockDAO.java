package com.github.it89.investordaybookspring.dao.interfaces;

import com.github.it89.investordaybookspring.daybook.stockmarket.SecurityStock;

public interface StockDAO {
    void register(SecurityStock stock);
    void remove(SecurityStock stock);
    boolean exists(SecurityStock stock);
    SecurityStock getStockByCode(String code);
    SecurityStock getStockByCaption(String caption);
}
