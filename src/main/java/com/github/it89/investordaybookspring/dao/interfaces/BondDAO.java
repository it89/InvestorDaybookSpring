package com.github.it89.investordaybookspring.dao.interfaces;

import com.github.it89.investordaybookspring.daybook.stockmarket.SecurityBond;

public interface BondDAO {
    void register(SecurityBond asset);
    void remove(SecurityBond asset);
    boolean exists(SecurityBond asset);
    SecurityBond getBondByCode(String code);
    SecurityBond getBondByCaption(String caption);
}
