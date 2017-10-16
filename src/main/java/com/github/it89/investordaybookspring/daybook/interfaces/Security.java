package com.github.it89.investordaybookspring.daybook.interfaces;

import com.github.it89.investordaybookspring.dao.interfaces.Storable;

public interface Security extends Storable {
    public String getCode();
    public String getCaption();
}
