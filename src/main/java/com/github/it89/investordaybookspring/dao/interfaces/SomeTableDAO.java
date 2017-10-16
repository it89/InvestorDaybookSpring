package com.github.it89.investordaybookspring.dao.interfaces;

import com.github.it89.investordaybookspring.dao.entities.SomeTableEntity;

import java.util.List;

public interface SomeTableDAO {
    public void save(SomeTableEntity t);
    public List<SomeTableEntity> list();
}
