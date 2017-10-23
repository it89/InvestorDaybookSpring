package com.github.it89.investordaybookspring.dao.interfaces;

import com.github.it89.investordaybookspring.dao.entities.SecurityEntity;

import java.util.List;

public interface SecurityDAO {
    public void merge(SecurityEntity entity);
    public SecurityEntity getEntityByISIN(String isin);
    public SecurityEntity getEntityByCodeGRN(String codeGRN);
    public SecurityEntity getEntityByCaption(String caption);
}
