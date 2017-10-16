package com.github.it89.investordaybookspring.dao.interfaces;

import com.github.it89.investordaybookspring.daybook.stockmarket.Stock;

public interface StockDAO {
    void register(Stock stock);
    void remove(Stock stock);
    boolean exists(Stock stock);
    Stock getStockByCode(String code);
    Stock getStockByCaption(String caption);
}
