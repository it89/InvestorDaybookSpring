package com.github.it89.investordaybookspring.daybook.stockmarket;

import java.util.HashMap;

public final class SecurityStock extends Security {
    private static final HashMap<String, SecurityStock> objects = new HashMap<>();

    private SecurityStock(String isin, SecurityType type) {
        super(isin, type);
    }

    public static SecurityStock getInstance(String isin) {
        if (objects.containsKey(isin)) {
            return objects.get(isin);
        } else {
            return new SecurityStock(isin, SecurityType.STOCK);
        }

    }
}
