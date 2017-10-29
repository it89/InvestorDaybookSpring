package com.github.it89.investordaybookspring.dao.interfaces;

import com.github.it89.investordaybookspring.dao.entities.DealEntity;
import com.github.it89.investordaybookspring.dao.entities.SecurityEntity;

public interface DealDAO {
    public void merge(DealEntity entity);
    public DealEntity getEntityByNumber(String dealNumber);
}
