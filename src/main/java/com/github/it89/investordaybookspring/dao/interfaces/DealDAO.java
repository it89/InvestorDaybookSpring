package com.github.it89.investordaybookspring.dao.interfaces;

import com.github.it89.investordaybookspring.dao.entities.DealEntity;
import com.github.it89.investordaybookspring.dao.entities.SecurityEntity;
import com.github.it89.investordaybookspring.daybook.stockmarket.Deal;
import com.github.it89.investordaybookspring.daybook.stockmarket.Security;

import java.util.List;

public interface DealDAO {
    public void merge(DealEntity entity);
    public DealEntity getEntityByNumber(String dealNumber);
    public List<Deal> getList();
    public List<Deal> getListBySecurity(Security security);
}
