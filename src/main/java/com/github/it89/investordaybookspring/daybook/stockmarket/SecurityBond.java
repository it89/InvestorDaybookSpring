package com.github.it89.investordaybookspring.daybook.stockmarket;

import java.util.HashMap;

public final class SecurityBond extends Security {
    private static final HashMap<String, SecurityBond> objects = new HashMap<>();

    private SecurityBond(String isin, SecurityType type) {
        super(isin, type);
    }

    public static SecurityBond getInstance(String isin) {
        if (objects.containsKey(isin)) {
            return objects.get(isin);
        } else {
            return new SecurityBond(isin, SecurityType.BOND);
        }

    }
}
