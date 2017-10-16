package com.github.it89.investordaybookspring.dao.interfaces;

import com.github.it89.investordaybookspring.daybook.stockmarket.Bond;
import com.github.it89.investordaybookspring.daybook.stockmarket.Stock;

public interface BondDAO {
    void register(Bond asset);
    void remove(Bond asset);
    boolean exists(Bond asset);
    Bond getBondByCode(String code);
    Bond getBondByCaption(String caption);
}
